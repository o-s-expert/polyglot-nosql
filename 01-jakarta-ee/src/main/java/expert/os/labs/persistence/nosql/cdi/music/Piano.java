package expert.os.labs.persistence.nosql.cdi.music;

import jakarta.enterprise.inject.Default;

@MusicalInstrument(InstrumentType.KEYBOARD)
@Default
class Piano implements Instrument {
    @Override
    public String sound() {
        return "piano";
    }
}
