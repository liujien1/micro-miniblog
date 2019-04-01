package website.liujie.common.base;

import website.liujie.common.page.PageModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liujie on 2016/9/29.
 */
public abstract class BaseServiceImpl<T>{

    public PageModel<T> queryForPage(PageModel<T> queryPage,List<T> result){
        queryPage.setResult(result);
        return queryPage;
    }

    public abstract long add(T entity);

    public abstract void update(T entity) ;

    public abstract void delete(T entity) ;

    public abstract void deleteById(Serializable id) ;

    public abstract T query(T entity) ;

    public abstract T queryById(Serializable id) ;

    public abstract List<T> queryForList(T entity) ;

}
