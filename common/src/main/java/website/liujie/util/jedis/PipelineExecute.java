package website.liujie.util.jedis;

import redis.clients.jedis.Client;
import redis.clients.jedis.Pipeline;

/**
 * 批量管道执行
 *
 * @author 刘杰
 * @date 2018-8-28
 */
public abstract class PipelineExecute extends Pipeline {
    public PipelineExecute() {
    }

    public abstract void execute() throws Exception;

    public void setClient(Client client) {
        super.setClient(client);
    }
}
