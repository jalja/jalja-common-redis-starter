package com.jalja.common.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.PooledByteBufAllocator;
import org.redisson.client.codec.BaseCodec;
import org.redisson.client.protocol.Decoder;
import org.redisson.client.protocol.Encoder;

/**
 * Redis 序列化
 * @author XL@doublefs.com
 * @date 1/18/23
 */
public class FastjsonCodec extends BaseCodec {
  @Override
  public Decoder<Object> getValueDecoder() {
    return (byteBuf, state) ->  {
      byte [] bytes=new byte[byteBuf.readableBytes()];
      byteBuf.readBytes(bytes);
      GenericFastJsonRedisSerializer serializer=new GenericFastJsonRedisSerializer();
      return serializer.deserialize(bytes);
    };
  }

  @Override
  public Encoder getValueEncoder() {
    return o -> {
      GenericFastJsonRedisSerializer serializer=new GenericFastJsonRedisSerializer();
      byte [] bytes=serializer.serialize(o);
      ByteBuf out = ByteBufAllocator.DEFAULT.buffer();
      ByteBufOutputStream os = new ByteBufOutputStream(out);
      os.write(bytes);
      return os.buffer();
    };
  }
}
