package euler.math;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;

import gnu.trove.list.array.TShortArrayList;

public class TestNumberUtils
{

    @Test
    public void testAsSequenceOfDigits()
    {
        URL resource = Resources.getResource("problem008_input.txt");
        File placeholder = new File(resource.getFile());
        TShortArrayList digits = ProblemDataUtils.asSequenceOfDigits(placeholder);
        ImmutableList<Integer> head = ImmutableList.of(7, 3, 1, 6, 7);
        ImmutableList<Integer> tail = ImmutableList.of(3, 4, 5, 0);

        assertEquals(24, digits.subList(0, 5)
                               .sum());
        assertEquals(12, digits.subList(digits.size() - 4, digits.size())
                               .sum());
    }

}
