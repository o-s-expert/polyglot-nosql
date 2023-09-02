package expert.os.labs.persistence.nosql.cdi.music;

@MusicalInstrument(InstrumentType.STRING)
class Violin implements Instrument {
    @Override
    public String sound() {
        return "violin";
    }
}
