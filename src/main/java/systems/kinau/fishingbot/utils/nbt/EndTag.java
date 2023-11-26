package systems.kinau.fishingbot.utils.nbt;

import systems.kinau.fishingbot.network.utils.ByteArrayDataInputWrapper;

public class EndTag extends Tag<Void> {

    @Override
    protected EndTag readNamed(ByteArrayDataInputWrapper in) {
        return read(in);
    }

    @Override
    protected EndTag read(ByteArrayDataInputWrapper in) {
        return this;
    }
}
