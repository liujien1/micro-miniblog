package website.liujie.util.jedis;

import redis.clients.jedis.Client;
import redis.clients.jedis.Transaction;

/**
 * redis事务执行器
 *
 * @author 刘杰
 * @date 2018-9-7
 */
public abstract class RedisTransactionExecute extends Transaction {

    public RedisTransactionExecute(Client client) {
        super(client);
    }

    public RedisTransactionExecute() {
    }

    public abstract Object execute() throws Exception;

    public void setClient(Client client) {
        this.client = client;
    }
}

