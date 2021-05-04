import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());

        assertThat(mockedList.get(0), is("first"));
        assertThat(mockedList.get(123), is(nullValue()));
        mockedList.get(1);
    }

    @Test
    public void makeStubUsingArgumentMatcher() {
        when(mockedList.get(anyInt())).thenReturn("element");
        assertThat(mockedList.get(999), is("element"));
        verify(mockedList).get(anyInt());
    }

    @Test
    public void verifyOptions() {
        mockedList.add("once");
        mockedList.add("twice");
        mockedList.add("twice");
        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");

        verify(mockedList).add("once");
        verify(mockedList, times(1)).add("once");
        verify(mockedList, times(2)).add("twice");
        verify(mockedList, times(3)).add("three times");
        verify(mockedList, never()).add("never");

        verify(mockedList, atMostOnce()).add("once");
        verify(mockedList, atLeastOnce()).add("three times");
        verify(mockedList, atLeast(2)).add("three times");
        verify(mockedList, atMost(5)).add("three times");
    }

    @Test
    public void verifyInOrder() {
        mockedList.add("was added first");
        mockedList.add("was added second");

        InOrder inOrder = inOrder(mockedList);
        inOrder.verify(mockedList).add("was added first");
        inOrder.verify(mockedList).add("was added second");
    }

    @Test
    public void verifyInOrderWithMultiMockObject() {
        List firstMock = mock(List.class);
        List secondMock = mock(List.class);

        firstMock.add("was called first");
        secondMock.add("was called second");
        firstMock.add("was called third");

        InOrder inOrder = inOrder(firstMock, secondMock);
        inOrder.verify(firstMock).add("was called first");
        inOrder.verify(secondMock).add("was called second");
        inOrder.verify(firstMock).add("was called third");

    }

    @Test
    public void verifyNoInteractionMockObject() {
        List mockOne = mock(List.class);
        List mockTwo = mock(List.class);
        List mockThree = mock(List.class);

        mockOne.add("one");

        verify(mockOne, never()).add("two");
        verifyNoInteractions(mockTwo, mockThree);
    }
}
