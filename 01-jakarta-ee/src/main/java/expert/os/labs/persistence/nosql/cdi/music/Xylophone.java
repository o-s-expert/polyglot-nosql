package expert.os.labs.persistence.nosql.cdi.music;

@MusicalInstrument(InstrumentType.PERCUSSION)
class Xylophone implements Instrument {
    @Override
    public String sound() {
        return "xylophone";
    }
}
