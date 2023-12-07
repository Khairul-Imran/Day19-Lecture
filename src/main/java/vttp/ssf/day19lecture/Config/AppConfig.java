package vttp.ssf.day19lecture.Config;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import vttp.ssf.day19lecture.Model.Employee;

@Configuration
public class AppConfig {

  private Logger logger = Logger.getLogger(AppConfig.class.getName()); // The logger's name is the name of the class.

  // Inject the properties from application.properties into the configuration.
  @Value("${spring.redis.host}")
  private String redisHost; // Railway: REDIS_HOST

  @Value("${spring.redis.port}")
  private Integer redisPort; // Railway: REDIS_PORT

  @Value("${spring.redis.username}")
  private String redisUser; // Railway: REDIS_USERNAME

  @Value("${spring.redis.password}")
  private String redisPassword; // Railway: REDIS_PASSWORD

  @Value("${spring.redis.database}")
  private Integer redisDatabase;

  // Daryl example.
  @Bean
  public JedisConnectionFactory jedisConnectionFactory() {

    // Sets your redis host and port (from application.properties).
    RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration(redisHost, redisPort);

    // Sets your redis username and password.
    if (redisUser != null && !redisUser.isEmpty()) {
      rsc.setUsername(redisUser);
    }

    if (redisPassword != null && !redisPassword.isEmpty()) {
      rsc.setPassword(redisPassword);
    }

    // Builds the client.
    JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
    // Because the Jedis Conneciton Factory requires the client.
    JedisConnectionFactory jedisFac = new JedisConnectionFactory(rsc, jedisClient);
    jedisFac.afterPropertiesSet(); // Performs validation of the configuration.

    return jedisFac;
  }

  @Bean
  public RedisTemplate<String, Object> redisObjectTemplate() {
    RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(jedisConnectionFactory());
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new StringRedisSerializer());

    return redisTemplate;
  }

  @Bean
  public RedisTemplate<String, Employee> redisEmployeeTemplate() {
    RedisTemplate<String,Employee> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(jedisConnectionFactory());
    // redisTemplate.setKeySerializer(new StringRedisSerializer());
    // redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    // redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());

    return redisTemplate;
  }



  // Chuck's

  // @Bean("myredis")
  // public RedisTemplate<String, String> createRedisConnection() { // <Key, Value>
  //   // Create a redis configuration.
  //   RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
  //   config.setHostName(redisHost);
  //   config.setPort(redisPort);
  //   config.setDatabase(redisDatabase);
  //   if (!"NOT_SET".equals(redisUser.trim())) {
  //     config.setUsername(redisUser);
  //     config.setPassword(redisPassword);
  //   }

  //   // NEVER log the username and password!
  //   logger.log(Level.INFO, "Using Redis database %d".formatted(redisPort));  // What level of log information do you want to give?
  //   logger.log(Level.INFO, "Using Redis password is set:%b".formatted(redisPassword != "NOT_SET"));

  //   JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
  //   JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
  //   jedisFac.afterPropertiesSet();

  //   RedisTemplate<String, String> template = new RedisTemplate<>();
  //   template.setConnectionFactory(jedisFac);

  //   template.setKeySerializer(new StringRedisSerializer());
  //   template.setValueSerializer(new StringRedisSerializer());
  //   template.setHashKeySerializer(new StringRedisSerializer());
  //   template.setHashValueSerializer(new StringRedisSerializer());
    
  //   return template;
  // }
}
