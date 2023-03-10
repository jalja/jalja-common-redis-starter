package com.jalja.common.component;

import com.jalja.common.config.FastjsonCodec;
import com.jalja.common.model.UrlModel;
import com.jalja.common.model.enums.RedisPatternEnum;
import com.jalja.common.properties.RedisCommonProperties;
import java.util.List;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.util.StringUtils;

/**
 * @author XL@doublefs.com
 * @date 1/18/23
 */
public class RedissonClientComponent {

  public RedissonClient redissonClient(RedisCommonProperties properties) {
    if (!StringUtils.hasLength(properties.getProjectName())){
      throw new RuntimeException("redis唯一标识不能为空");
    }
    RedisPatternEnum redisPatternEnum=RedisPatternEnum.getEnum(properties.getPattern());
    String [] urls=getUrl(properties.getUrls());
    Config config = new Config();
    config.setCodec(new FastjsonCodec());
    switch (redisPatternEnum) {
      case SINGLE :
        config.useSingleServer().setAddress(urls[0]);
        config.useSingleServer().setDatabase(properties.getDbNum());
        if (StringUtils.hasLength(properties.getPassword())) {
          config.useSingleServer().setPassword(properties.getPassword());
        }
        break;
      case CLUSTER:
        config.useClusterServers().addNodeAddress(urls);
        if (StringUtils.hasLength(properties.getPassword())) {
          config.useClusterServers().setPassword(properties.getPassword());
        }
        break;
      case SENTRY:
        config.useSentinelServers().addSentinelAddress(urls);
        config.useSentinelServers().setDatabase(properties.getDbNum());
        if (StringUtils.hasLength(properties.getPassword())) {
          config.useSentinelServers().setPassword(properties.getPassword());
        }
        break;
      default:
        throw new RuntimeException("模式错误");
    }
    return Redisson.create(config);
  }
  private String [] getUrl(List<UrlModel> models){
    String[] urls=new String[models.size()];
    int i=0;
    for (UrlModel model:models){
      urls[i]="redis://" + model.getHost() + ":" + model.getPort();
      i++;
    }
    return urls;
  }
}
