##### kafka配置

```
bootstrap.servers:kafka server的地址

acks:写入kafka时，leader负责一个该partion读写，当写入partition时，需要将记录同步到repli节点，all是全部同步节点都返回成功，leader才返回ack。

retris:写入失败时，重试次数。当leader节点失效，一个repli节点会替代成为leader节点，此时可能出现写入失败，当retris为0时，produce不会重复。retirs重发，此时repli节点完全成为leader节点，不会产生消息丢失。

batch.size:produce积累到一定数据，一次发送。

buffer.memory: produce积累数据一次发送，缓存大小达到buffer.memory就发送数据。

linger.ms :当设置了缓冲区，消息就不会即时发送，如果消息总不够条数、或者消息不够buffer大小就不发送了吗？当消息超过linger时间，也会发送。

key/value serializer：序列化类。
```

### 关于kafka序列化/反序列化

https://www.jianshu.com/p/5da86afed228

自定义Serializer和Deserializer非常痛苦，还有很多类型不支持，非常脆弱。复杂类型的支持更是一件痛苦的事情，不同版本之间的兼容性问题更是一个极大的挑战。由于Serializer和Deserializer影响到上下游系统，导致牵一发而动全身。自定义序列化&反序列化实现不是能力的体现，而是逗比的体现。所以强烈不建议自定义实现序列化&反序列化，推荐直接使用StringSerializer和StringDeserializer，然后使用json作为标准的数据传输格式。