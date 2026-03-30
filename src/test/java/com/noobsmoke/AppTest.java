package com.noobsmoke;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

public class AppTest {

    private final List<String> mockList = mock();
    private final List<String> realList = new ArrayList<>();

    @Test
    void myFirstTestWithMock() {
        mockList.add("hello");
        when(mockList.get(0)).thenReturn("hello");
        verify(mockList).add("hello");
        assertThat(mockList.get(0)).isEqualTo("hello");
    }

    @Test
    void myFirstTestWithoutMock() {
        realList.add("Hello");
        assertThat(realList).hasSize(1);
    }


    @Test
    void shouldVerifyNoInteractions() {
        List<String> mockOfList = mock();
        verifyNoInteractions(mockOfList);
    }

    @Test
    void shouldVerifyNoMoreInteractions() {
        List<String> list = mock();
        list.clear();
        verify(list).clear();
        verifyNoMoreInteractions(list);
    }

    @Test
    void shouldVerifyInteractionMode() {
        List<String> list = mock();
        list.clear();
        list.clear();

        verify(list, times(2)).clear();
        verifyNoMoreInteractions(list);
    }

}