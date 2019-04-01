package website.liujie.common.page;


import java.util.List;

/**
 * @Description	: 
 * @Copyright	: Excenon. ALL Rights Reserved
 * @Company		: 深圳市华磊移动设备科技有限公司 
 * @author		: Cosmo
 * @version		: 1.0
 * @Date		: 2015年11月27日 下午8:04:01
 */
public class PageModel<T>{

	/*
	 * page size
	 * */
	private int pageSize = 10;
	
	/*
	 * total size
	 * */
	private int totalSize = 0;
	
	/*
	 * total page
	 * */
	private int totalPage = 0;
	
	/*
	 * current page number 
	 * */
	private int pageIndex = 1;

	private T beanParams;

	private List<T> result;
	
	public PageModel(){}

	/*
	 * get the start page index
	 * */
	public int getStartPageIndex(){
		return 1;
	}

	public String limitStr;
	
	/*
	 * get previous page index
	 * */
	public int getPrevPageIndex(){
		if(this.pageIndex<=1){
			return 1;
		}
		return pageIndex-1;
	}
	
	/*
	 * get the next page index
	 * */
	public int getNextPageIndex(){
		if(this.pageIndex>=this.totalPage){
			return this.totalPage;
		}
		return pageIndex+1;
	}
	
	/*
	 * get the end page index
	 * */
	public int getEndPageIndex(){
		return this.totalPage;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
		this.totalPage = (int)Math.ceil(Double.valueOf(this.totalSize)/Double.valueOf(this.pageSize));
		/*if(this.pageIndex>=this.totalPage){
			this.pageIndex = this.totalPage;
		}*/
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public T getBeanParams() {
		return beanParams;
	}

	public void setBeanParams(T beanParams) {
		this.beanParams = beanParams;
	}

	public String getLimitStr() {
		return " limit "+(this.pageIndex-1)*this.pageSize+","+this.pageSize;
	}


	@Override
	public String toString() {
		StringBuffer str = new StringBuffer(getClass().getName()+"@");
		str.append("pageSize="+pageSize+";");
		str.append("totalSize="+totalSize+";");
		str.append("totalPage="+totalPage+";");
		str.append("pageIndex="+pageIndex+";");
		return str.toString();
	}
}
