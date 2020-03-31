package euler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.math3.util.FastMath;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;

import gnu.trove.map.TLongLongMap;
import gnu.trove.map.hash.TLongLongHashMap;

public class TestMavenBuild
{

    @Before
    public void setUp() throws Exception
    {
    }

    @Test
    public void test() throws IOException
    {
        ImmutableList<Integer> ints = ImmutableList.of(2, 3, 4);
        assertTrue(ints.size() == 3);

        int three = FastMath.round(2.5F);
        assertTrue(3 == three);        
        
        TLongLongMap tll = new TLongLongHashMap();
        tll.put(12345L, 567890L);
        assertTrue(tll.values().length == 1);

        URL resource = Resources.getResource("testMavenBuild.data");
        String hellohello = Resources.toString(resource, Charset.defaultCharset()).trim();
        assertEquals("hello hello", hellohello);
        
    }

}
