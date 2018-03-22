package netty.io.demo.apply.idle;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttPublishVariableHeader;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.util.CharsetUtil;

/**
 * 
 * @author yuezh2   2018年3月19日 下午4:55:19
 *
 */
public class MqttUtils {
	
	private static final ByteBufAllocator ALLOCATOR = new UnpooledByteBufAllocator(false);

	
	/**
	 * 包装mqtt协议消息
	 * @param message
	 * @return
	 */
	public static MqttPublishMessage createPublishMessage(String message) {
        MqttFixedHeader mqttFixedHeader =
                new MqttFixedHeader(MqttMessageType.PUBLISH, false, MqttQoS.AT_LEAST_ONCE, true, 0);
        MqttPublishVariableHeader mqttPublishVariableHeader = new MqttPublishVariableHeader("/abc", 1234);
        ByteBuf payload =  ALLOCATOR.buffer();
        payload.writeBytes(message.getBytes(CharsetUtil.UTF_8));
        payload.clear();
        return new MqttPublishMessage(mqttFixedHeader, mqttPublishVariableHeader, payload);
    }
	
	
}
