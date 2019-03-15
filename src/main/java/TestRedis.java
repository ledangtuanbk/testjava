import redis.clients.jedis.Jedis;

import java.util.List;

public class TestRedis {
    public static void main(String[] args) {
        // Connecting to Redis server on localhost
        Jedis jedis = new Jedis("localhost");

        System.out.println("Connection to server sucessfully");

        // store data in redis list
//        jedis.rpush("KEY_A", "Redis");
//        jedis.rpush("KEY_A", "Mongodb");
//        jedis.rpush("KEY_A", "Mysql");
        // Get the stored data and print it
        List<String> list = jedis.lrange("KEY_A", 0, -1);

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}
