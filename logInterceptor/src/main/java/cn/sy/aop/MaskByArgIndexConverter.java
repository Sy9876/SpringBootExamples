package cn.sy.aop;

import org.apache.log4j.Logger;

/**
 * 按参数索引屏蔽的转换器
 * 构造函数接收参数索引或索引数组，转换时会屏蔽这些参数。
 * @author Administrator
 *
 */
public class MaskByArgIndexConverter extends DefaultArgConverter {
	private static Logger logger = Logger.getLogger(MaskByArgIndexConverter.class);
	
	private static final String MASK_STR = "\"...\"";
	
//	private List<Integer> indexes = new ArrayList<Integer>();
//	public MaskByArgIndexConverter(int index) {
//		indexes.add(index);
//	}
//	public MaskByArgIndexConverter(int[] index) {
//		for(int idx : index) {
//			indexes.add(idx);
//		}
//		
//	}
	private int[] indexes = new int[1];
	public MaskByArgIndexConverter(int index) {
		indexes[0] = index;
	}
	public MaskByArgIndexConverter(int[] indexes) {
		this.indexes = indexes;
		
	}
	
	@Override
	public String onPostProcessArg(int argIdx, Object arg, String argStr) {
		// 匹配索引数组的参数，转换为 "..."，其它的原样输出
		for(int idx : indexes) {
			if(argIdx==idx) {
				return MASK_STR;
			}
		}
		return argStr;
	}

}
