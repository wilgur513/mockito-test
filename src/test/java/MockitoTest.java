import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MockitoTest {
    @Mock
    List mockedList;

    @Test
    public void makeMockObject() {
        List mockedList = mock(List.class);

        mockedList.add("one");
        mockedList.clear();

        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

    @Test
    public void makeMockObjectUsingRunner() {
        mockedList.add("one");
        mockedList.clear();

        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

    @Test(expected=RuntimeException.class)
    public void makeStub() {
        LinkedList mockedList = mock(LinkedList.class);

        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());

        assertThat(mockedList.get(0), is("first"));
        assertThat(mockedList.get(123), is(nullValue()));
        mockedList.get(1);
    }
}
