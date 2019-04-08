package website.liujie.common.base;

import org.apache.ibatis.annotations.Param;
import website.liujie.common.page.PageInterceptor;
import website.liujie.common.page.PageModel;

import java.io.Serializable;
import java.util.List;

/**
 * @Description	: 
 * @Copyright	: Excenon. ALL Rights Reserved
 * @Company		: jie 
 * @author		: Cosmo
 * @version		: 1.0
 * @Date		: 2015年11月28日 上午11:21:36
 * @param <T>
 */
public interface BaseDao<T extends BaseBean> {

	/**  
	 * add (增加一个实体到数据库中，如果数据库主键是自动增长，保持成功后，会将主键填充到实体中)  
	 * @param entity  
	 * @author Cosmo 
	 * @return void  
	 * @exception   
	 * @since  1.0.0  
	 */
	long add(T entity);
	
	/**  
	 * update (更新一个实体到数据库中)  
	 * @param entity  
	 * @author Cosmo 
	 * @return void  
	 * @exception   
	 * @since  1.0.0  
	 */
	void update(T entity);

	/**  
	 * delete (删除一个实体从数据库中)  
	 * @param entity  
	 * @author Cosmo 
	 * @return void  
	 * @exception   
	 * @since  1.0.0  
	 */
	void delete(T entity);
	
	/**  
	 * delete (通过id删除一个实体从数据库中)  
	 * @param entity  
	 * @author Cosmo 
	 * @return void  
	 * @exception   
	 * @since  1.0.0  
	 */
	void deleteById(Serializable id);

	/**  
	 * query (查询一个实体从数据库中) 
	 * @param entity  
	 * @author Cosmo 
	 * @return T  
	 * @exception   
	 * @since  1.0.0  
	 */
	T query(T entity);
	
	/**  
	 * query (通过ID查询一个实体从数据库中) 
	 * @param entity  
	 * @author Cosmo 
	 * @return T  
	 * @exception   
	 * @since  1.0.0  
	 */
	T queryById(Serializable id);

	/**
	 * queryForList (通过实体信息查询一个实体集合从数据库中)
	 * @param entity
	 * @author Cosmo
	 * @return List<T>
	 * @exception
	 * @since  1.0.0
	 */
	List<T> queryForList(T entity);

	String PO_KEY = "po";

	Integer queryForPageCount(@Param(PageInterceptor.PAGE_KEY) PageModel<T> p, @Param(PO_KEY) T obj);

	/**
	 * 分页
	 * @return
	 */
	List<T> queryForPage(@Param(PageInterceptor.PAGE_KEY) PageModel<T> p, @Param(PO_KEY) T obj);

}
