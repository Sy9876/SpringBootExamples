package cn.sy.config;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import java.util.Random;

public class MyShardingBalance {

	/**
	 * 使用数据量最小的分区
	 * 相同支部尽量合并，但分区间数据量差不大于阈值
	 *
	 */
	
	// 花店-分区
	private Map<String, String> shopPartitionMap = new HashMap<String, String>();
	// 支部-分区（分区列表。用逗号分隔）
	private Map<String, String> branchPartitionMap = new HashMap<String, String>();
	// 分区-数据量
	private Map<String, Integer> partitionLoadMap = new HashMap<String, Integer>();
	// 分区-花店数量
	private Map<String, Integer> partitionShopCntMap = new HashMap<String, Integer>();
	// 分区统计数据
	private List<PartitionStats> partitionStatsList = new ArrayList<PartitionStats>();
	
	// 分区数
	private int shardingSize = 10;
	// 阈值 选定分区负载与所有分区最低负载
	private static final int vavle = 10000;
	// 阈值2 低负载列表
	private static final int vavle2 = 10000;
	
	/**
	 * 根据branchCode查出分区列表，
	 *   如果没有，则使用负载最小的分区。
	 * 
	 * 选取分区列表中负载最小的分区，与负载最小的分区比较，
	 *   如果差大于阈值，则使用负载最小分区，
	 *   否则使用此支部分区列表中负载最小的分区
	 *   
	 * @param branchCode
	 * @param shopCode
	 * @return
	 */
	public String doBalance(String branchCode, String shopCode) {
		String result = null;
		
		
		// 如果在花店-分区表中有，则返回
		result = shopPartitionMap.get(shopCode);
		if(result!=null) {
			return result;
		}
				
		// 根据支部获取支部-分区列表
		String branchPatitions = branchPartitionMap.get(branchCode);
		if(branchPatitions==null) {
			// 支部不存在则查找负载最低的分区
//			result = findLowestLoadPartition();
			result = findLowestLoadPartitionWithShopCnt();

			updateStats(branchCode, shopCode, result);
			
			return result;
		}
		else {
			// 查找此支部的分区列表中负载最低的
			String[] parsForBranch = branchPatitions.split(",");
			String parForBranch = findLowestLoadPartitionByList(parsForBranch);
			int loadForBranch = partitionLoadMap.get(parForBranch);
			// 比较上面得到的负载与所有分区中最低负载，超过阈值则使用负载最低的新分区
//			String lowestLoadPar = findLowestLoadPartition();
			String lowestLoadPar = findLowestLoadPartitionWithShopCnt();
			int lowestLoadParLoad  = partitionLoadMap.get(lowestLoadPar);
			if(loadForBranch - lowestLoadParLoad > vavle) {
				result = lowestLoadPar;

				updateStats(branchCode, shopCode, result);
				
			}
			else {
				result = parForBranch;

				updateStats(branchCode, shopCode, result);
			}
		}
		
		return result;
	}
	
	private void updateStats(String branchCode, String shopCode, String part) {
		
		// 写入 支部-分区表
		boolean found = false;
		String branchPatitions = branchPartitionMap.get(branchCode);
		if(branchPatitions==null) {
			branchPartitionMap.put(branchCode, part);
		}
		else {
			for(String p : branchPatitions.split(",")) {
				if(p.equals(part)) {
					found=true;
					break;
				}
			}
			if(!found) {
				branchPartitionMap.put(branchCode, branchPatitions+","+part);
			}
			
		}

		// 写入 花店-分区表
		shopPartitionMap.put(shopCode, part);
		
		// 写入分区-花店数量
		partitionShopCntMap.put(part, partitionShopCntMap.get(part) + 1);

		// 更新分区统计数据
		for(PartitionStats pStats : partitionStatsList) {
			if(pStats.getPartition().equals(part)) {
				int shopCnt = pStats.getShopCnt();
				pStats.setShopCnt(shopCnt + 1);
			}
		}

		
	}
	
	// 从全分区中查找负载最小的
	private String findLowestLoadPartition() {
		String par = null;
		int tmpLoad = Integer.MAX_VALUE;
		
		for(Entry<String, Integer> e : partitionLoadMap.entrySet()) {
			if(e.getValue()<=tmpLoad) {
				tmpLoad = e.getValue();
				par = e.getKey();
			}
		}
		
		return par;
	}
	
	private String findLowestLoadPartitionWithShopCnt() {
		String par = null;
		int tmpLoad = Integer.MAX_VALUE;
		int lowestLoad = Integer.MAX_VALUE;
		
		List<PartitionStats> lowLoadPartitionList = new ArrayList<PartitionStats>();
		// 按负载排序
		partitionStatsList.sort(
			new Comparator<PartitionStats>() {
				@Override
			    public int compare(PartitionStats o1, PartitionStats o2) {
			        return o1.getLoad() - o2.getLoad();
			    }
			}
		);
		// 提取负载不超过vavle2的分区
		for(int i=0;i<partitionStatsList.size();i++) {
			tmpLoad = partitionStatsList.get(i).getLoad();
			if(tmpLoad < lowestLoad) {
				lowestLoad = tmpLoad;
			}
			if( tmpLoad - lowestLoad > vavle2) {
				break;
			}
			lowLoadPartitionList.add(partitionStatsList.get(i));
		}
		
		// 负载不超过vavle2的分区 按花店数量排序
		lowLoadPartitionList.sort(
			new Comparator<PartitionStats>() {
				@Override
			    public int compare(PartitionStats o1, PartitionStats o2) {
			        return o1.getShopCnt() - o2.getShopCnt();
			    }
			}
		);
		
		par = lowLoadPartitionList.get(0).getPartition();

		return par;
	}

	// 从分区列表中查找负载最小的
	private String findLowestLoadPartitionByList(String[] parList) {
		int tmpLoad = 0;
		int minLoad = Integer.MAX_VALUE;
		String minPar = null;
		
		for(String p : parList) {
			tmpLoad = partitionLoadMap.get(p);

			if(tmpLoad<=minLoad) {
				minLoad = tmpLoad;
				minPar = p;
			}
		}
		
		return minPar;
	}
	
	public void initPartitionLoadMap() {
		for(int i=0;i<shardingSize;i++) {
			partitionLoadMap.put(i+"", 0);
			partitionShopCntMap.put(i+"", 0);
			partitionStatsList.add(new PartitionStats(i+"", 0, 0));
		}
	}
	
	public int incrLoad(String partition, int load) {
		int l = partitionLoadMap.get(partition);
		l += load;
		partitionLoadMap.put(partition, l);
		
		for(PartitionStats pStats : partitionStatsList) {
			if(pStats.getPartition().equals(partition)) {
				pStats.setLoad(l);
			}
		}
		
		return l;
	}
	
	public void showLoad() {
		int l = 0;
		System.out.println("partitionLoadMap");
		for(int i=0;i<shardingSize;i++) {
			l = partitionLoadMap.get(i+"");
			System.out.println(i + ": " + l);
		}
		
		System.out.println("branchPartitionMap");
		for(Entry<String, String> e : branchPartitionMap.entrySet()) {
			System.out.println(e.getKey() + ": " + e.getValue());
		}
		
//		System.out.println("shopPartitionMap");
//		for(Entry<String, String> e : shopPartitionMap.entrySet()) {
//			System.out.println(e.getKey() + ": " + e.getValue());
//		}
		
		System.out.println("partitionStatsList");
		// 按负载排序
		partitionStatsList.sort(
			new Comparator<PartitionStats>() {
				@Override
			    public int compare(PartitionStats o1, PartitionStats o2) {
			        return o1.getLoad() - o2.getLoad();
			    }
			}
		);
		for(PartitionStats pStats : partitionStatsList) {
			System.out.println(pStats);
		}
		
	}
	
	// 测试
	public void test() {
		String branch;
		String shop;
		String par;
		Random random = new Random();
		int shopCnt = 1000;
		String[] shopList = new String[shopCnt];
		Map<String, Integer> branchLoad = new HashMap<String, Integer>();
		int[] shopLoad = new int[shopCnt];
		
		// 模拟1000个花店
		for(int i=0;i<shopCnt;i++) {
			shopList[i]=UUID.randomUUID().toString();
		}
		
		// 模拟100万交易
		for(int i=0;i<100000;i++) {
			int ran = Math.abs(random.nextInt() % shopCnt);
			int orderCnt;
			shop=shopList[ran];
			branch=shop.substring(0, 1);
			
			par=doBalance(branch, shop);
//			System.out.println("doBalance. got par=" + par + "  branchCode=" + branch + "  shopCode:" + shop);
			orderCnt=ran%100;
			if(ran<300) {
				orderCnt=ran*10;
			}
			incrLoad(par, orderCnt);
			Integer bLoad = branchLoad.get(branch);
			branchLoad.put(branch, bLoad==null?0:bLoad + orderCnt);
			shopLoad[ran]+=orderCnt;
		}
		
		showLoad();
		System.out.println("branchLoad");
		for(Entry<String, Integer> e : branchLoad.entrySet()) {
			System.out.println(e.getKey() + ": " + e.getValue());
		}
//		System.out.println("shopLoad");
//		for(int i=0;i<shopCnt;i++) {
//			System.out.println(i + ": " + shopLoad[i]);
//		}
	}
	
	public static void main(String[] args) {
		MyShardingBalance obj = new MyShardingBalance();
		obj.initPartitionLoadMap();
		
		obj.test();
		
	}
	
	public class PartitionStats {
		// 分区
		String partition;
		// 数据量
		int load;
		// 花店数量
		int shopCnt;
		
		public PartitionStats(String partition, int load, int shopCnt) {
			super();
			this.partition = partition;
			this.load = load;
			this.shopCnt = shopCnt;
		}

		public String getPartition() {
			return partition;
		}

		public void setPartition(String partition) {
			this.partition = partition;
		}

		public int getLoad() {
			return load;
		}

		public void setLoad(int load) {
			this.load = load;
		}

		public int getShopCnt() {
			return shopCnt;
		}

		public void setShopCnt(int shopCnt) {
			this.shopCnt = shopCnt;
		}

		@Override
		public String toString() {
			return "partition=" + partition + " load=" + load + " shopCnt=" + shopCnt;
		}
	}
}
