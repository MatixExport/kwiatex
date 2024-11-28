package indie.outsource.repositories.redis;

import lombok.Getter;
import redis.clients.jedis.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Getter
public class DefaultRedisConnection {
    private JedisPooled pool;

    public DefaultRedisConnection(String propertyFile){
        Properties properties = new Properties();
        String host;
        int port;
        try (FileInputStream input = new FileInputStream(propertyFile)) {
            properties.load(input);
            host = properties.getProperty("redis.host");
            port = Integer.parseInt(properties.getProperty("redis.port"));
            JedisClientConfig clientConfig = DefaultJedisClientConfig.builder().build();
            pool = new JedisPooled(new HostAndPort(host, port), clientConfig);
        }
        catch (IOException _){
            System.out.println("IO exception");
        }
    }
}
