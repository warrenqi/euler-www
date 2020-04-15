package euler.dbs;

public enum RocksCols
{
    DEFAULT("default".getBytes()), 
    FIBS_INT64("FIBS_INT64".getBytes()), 
    PRIMES_INT64("PRIMES_INT64".getBytes());

    public final byte[] cName;

    RocksCols(byte[] cName)
    {
        this.cName = cName;
    }
}
