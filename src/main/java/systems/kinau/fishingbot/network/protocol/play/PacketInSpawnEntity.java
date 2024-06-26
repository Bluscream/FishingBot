/*
 * Created by David Luedtke (MrKinau)
 * 2019/5/5
 */

package systems.kinau.fishingbot.network.protocol.play;

import com.google.common.io.ByteArrayDataOutput;
import lombok.Getter;
import lombok.NoArgsConstructor;
import systems.kinau.fishingbot.FishingBot;
import systems.kinau.fishingbot.event.play.SpawnEntityEvent;
import systems.kinau.fishingbot.network.protocol.NetworkHandler;
import systems.kinau.fishingbot.network.protocol.Packet;
import systems.kinau.fishingbot.network.protocol.ProtocolConstants;
import systems.kinau.fishingbot.network.utils.ByteArrayDataInputWrapper;

@Getter
@NoArgsConstructor
public class PacketInSpawnEntity extends Packet {

    private int id;
    private int type;
    private double x;
    private double y;
    private double z;
    private byte yaw;
    private byte pitch;
    private byte headYaw;
    private int objectData;
    private short xVelocity;
    private short yVelocity;
    private short zVelocity;

    @Override
    public void write(ByteArrayDataOutput out, int protocolId) {
    }

    @Override
    public void read(ByteArrayDataInputWrapper in, NetworkHandler networkHandler, int length, int protocolId) {
        if (protocolId == ProtocolConstants.MC_1_8) {
            this.id = readVarInt(in);       // EID
            this.type = in.readByte();      // Type of the object
            this.x = in.readInt() / 32.0;   // X POS
            this.y = in.readInt() / 32.0;   // Y POS
            this.z = in.readInt() / 32.0;   // Z POS
            this.pitch = in.readByte();     // Pitch
            this.yaw = in.readByte();       // Yaw
            this.objectData = in.readInt(); // Data of object: https://wiki.vg/Object_Data
            if (getObjectData() != 0) {
                this.xVelocity = in.readShort();    // Velocity only present if data nonzero
                this.yVelocity = in.readShort();
                this.zVelocity = in.readShort();
            }
        } else if (protocolId < ProtocolConstants.MC_1_19) {
            this.id = readVarInt(in);       // EID
            readUUID(in);                   // E UUID
            this.type = in.readByte();      // Obj type
            this.x = in.readDouble();  // X POS (casts are incorrect, but if nobody sees it, nobody can blame it)
            this.y = in.readDouble();  // Y POS
            this.z = in.readDouble();  // Z POS
            this.pitch = in.readByte();     // Pitch
            this.yaw = in.readByte();       // Yaw
            this.objectData = in.readInt(); // Data of object: https://wiki.vg/Object_Data
            if (getObjectData() != 0) {
                this.xVelocity = in.readShort();    // Velocity only present if data nonzero
                this.yVelocity = in.readShort();
                this.zVelocity = in.readShort();
            }
        } else {
            this.id = readVarInt(in);               // EID
            readUUID(in);                           // E UUID
            this.type = readVarInt(in);             // Obj type
            this.x = in.readDouble();               // X POS (casts are incorrect, but if nobody sees it, nobody can blame it)
            this.y = in.readDouble();               // Y POS
            this.z = in.readDouble();               // Z POS
            this.pitch = in.readByte();             // Pitch
            this.yaw = in.readByte();               // Yaw
            this.headYaw = in.readByte();           // head yaw
            this.objectData = readVarInt(in);       // Data of object: https://wiki.vg/Object_Data
            if (getObjectData() != 0) {
                this.xVelocity = in.readShort();    // Velocity only present if data nonzero
                this.yVelocity = in.readShort();
                this.zVelocity = in.readShort();
            }
        }

        FishingBot.getInstance().getCurrentBot().getEventManager().callEvent(
                new SpawnEntityEvent(getId(), getType(), getX(), getY(), getZ(), getYaw(), getPitch(), getObjectData(), getXVelocity(), getYVelocity(), getZVelocity()));
    }
}
