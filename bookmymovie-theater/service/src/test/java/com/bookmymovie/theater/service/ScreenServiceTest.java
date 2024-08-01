package com.bookmymovie.theater.service;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.core.error.Error;
import com.bookmymovie.theater.constant.ExceptionConstants;
import com.bookmymovie.theater.converter.ScreenConverter;
import com.bookmymovie.theater.helper.StatusMapper;
import com.bookmymovie.theater.model.Screen;
import com.bookmymovie.theater.model.ScreenRequest;
import com.bookmymovie.theater.model.ScreenResponse;
import com.bookmymovie.theater.repository.ScreenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScreenServiceTest {

    @Mock
    private ScreenConverter mockScreenConverter;
    @Mock
    private ScreenRepository mockScreenRepository;
    @Mock
    private StatusMapper mockStatusMapper;

    @InjectMocks
    private ScreenService screenServiceUnderTest;

    @Test
    void testSaveScreen() throws Exception {
        // Setup
        final ScreenRequest screenRequest = ScreenRequest.builder()
                .screen(Screen.builder()
                        .screenId(0L)
                        .operational(false)
                        .build())
                .build();
        final ScreenResponse expectedResult = ScreenResponse.builder()
                .screens(List.of(Screen.builder()
                        .screenId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure ScreenConverter.convertModelToEntity(...).
        final com.bookmymovie.theater.entity.Screen screen = com.bookmymovie.theater.entity.Screen.builder()
                .operational(false)
                .build();
        when(mockScreenConverter.convertModelToEntity(Screen.builder()
                .screenId(0L)
                .operational(false)
                .build())).thenReturn(screen);

        // Configure ScreenRepository.save(...).
        final com.bookmymovie.theater.entity.Screen screen1 = com.bookmymovie.theater.entity.Screen.builder()
                .operational(false)
                .build();
        when(mockScreenRepository.save(com.bookmymovie.theater.entity.Screen.builder()
                .operational(false)
                .build())).thenReturn(screen1);

        // Configure ScreenConverter.convertEntityToModel(...).
        final Screen screen2 = Screen.builder()
                .screenId(0L)
                .operational(false)
                .build();
        when(mockScreenConverter.convertEntityToModel(com.bookmymovie.theater.entity.Screen.builder()
                .operational(false)
                .build())).thenReturn(screen2);

        // Run the test
        final ScreenResponse result = screenServiceUnderTest.saveScreen(screenRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(ScreenResponse.builder()
                .screens(List.of(Screen.builder()
                        .screenId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testSaveScreen_ScreenConverterConvertModelToEntityThrowsCoversionException() throws Exception {
        // Setup
        final ScreenRequest screenRequest = ScreenRequest.builder()
                .screen(Screen.builder()
                        .screenId(0L)
                        .operational(false)
                        .build())
                .build();
        final ScreenResponse expectedResult = ScreenResponse.builder()
                .screens(List.of(Screen.builder()
                        .screenId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockScreenConverter.convertModelToEntity(Screen.builder()
                .screenId(0L)
                .operational(false)
                .build())).thenThrow(CoversionException.class);
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final ScreenResponse result = screenServiceUnderTest.saveScreen(screenRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testSaveScreen_ScreenConverterConvertEntityToModelThrowsCoversionException() throws Exception {
        // Setup
        final ScreenRequest screenRequest = ScreenRequest.builder()
                .screen(Screen.builder()
                        .screenId(0L)
                        .operational(false)
                        .build())
                .build();
        final ScreenResponse expectedResult = ScreenResponse.builder()
                .screens(List.of(Screen.builder()
                        .screenId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure ScreenConverter.convertModelToEntity(...).
        final com.bookmymovie.theater.entity.Screen screen = com.bookmymovie.theater.entity.Screen.builder()
                .operational(false)
                .build();
        when(mockScreenConverter.convertModelToEntity(Screen.builder()
                .screenId(0L)
                .operational(false)
                .build())).thenReturn(screen);

        // Configure ScreenRepository.save(...).
        final com.bookmymovie.theater.entity.Screen screen1 = com.bookmymovie.theater.entity.Screen.builder()
                .operational(false)
                .build();
        when(mockScreenRepository.save(com.bookmymovie.theater.entity.Screen.builder()
                .operational(false)
                .build())).thenReturn(screen1);

        when(mockScreenConverter.convertEntityToModel(com.bookmymovie.theater.entity.Screen.builder()
                .operational(false)
                .build())).thenThrow(CoversionException.class);
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final ScreenResponse result = screenServiceUnderTest.saveScreen(screenRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testUpdateScreenOperational() throws Exception {
        // Setup
        final ScreenRequest screenRequest = ScreenRequest.builder()
                .screen(Screen.builder()
                        .screenId(0L)
                        .operational(false)
                        .build())
                .build();
        final ScreenResponse expectedResult = ScreenResponse.builder()
                .screens(List.of(Screen.builder()
                        .screenId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure ScreenRepository.findById(...).
        final Optional<com.bookmymovie.theater.entity.Screen> screen = Optional.of(
                com.bookmymovie.theater.entity.Screen.builder()
                        .operational(false)
                        .build());
        when(mockScreenRepository.findById(0L)).thenReturn(screen);

        // Configure ScreenRepository.save(...).
        final com.bookmymovie.theater.entity.Screen screen1 = com.bookmymovie.theater.entity.Screen.builder()
                .operational(false)
                .build();
        when(mockScreenRepository.save(com.bookmymovie.theater.entity.Screen.builder()
                .operational(false)
                .build())).thenReturn(screen1);

        // Configure ScreenConverter.convertEntityToModel(...).
        final Screen screen2 = Screen.builder()
                .screenId(0L)
                .operational(false)
                .build();
        when(mockScreenConverter.convertEntityToModel(com.bookmymovie.theater.entity.Screen.builder()
                .operational(false)
                .build())).thenReturn(screen2);

        // Run the test
        final ScreenResponse result = screenServiceUnderTest.updateScreenOperational(screenRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(ScreenResponse.builder()
                .screens(List.of(Screen.builder()
                        .screenId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testUpdateScreenOperational_ScreenRepositoryFindByIdReturnsAbsent() {
        // Setup
        final ScreenRequest screenRequest = ScreenRequest.builder()
                .screen(Screen.builder()
                        .screenId(0L)
                        .operational(false)
                        .build())
                .build();
        final ScreenResponse expectedResult = ScreenResponse.builder()
                .screens(List.of(Screen.builder()
                        .screenId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockScreenRepository.findById(0L)).thenReturn(Optional.empty());
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.RECORD_NOT_FOUND_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final ScreenResponse result = screenServiceUnderTest.updateScreenOperational(screenRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testUpdateScreenOperational_ScreenConverterThrowsCoversionException() throws Exception {
        // Setup
        final ScreenRequest screenRequest = ScreenRequest.builder()
                .screen(Screen.builder()
                        .screenId(0L)
                        .operational(false)
                        .build())
                .build();
        final ScreenResponse expectedResult = ScreenResponse.builder()
                .screens(List.of(Screen.builder()
                        .screenId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure ScreenRepository.findById(...).
        final Optional<com.bookmymovie.theater.entity.Screen> screen = Optional.of(
                com.bookmymovie.theater.entity.Screen.builder()
                        .operational(false)
                        .build());
        when(mockScreenRepository.findById(0L)).thenReturn(screen);

        // Configure ScreenRepository.save(...).
        final com.bookmymovie.theater.entity.Screen screen1 = com.bookmymovie.theater.entity.Screen.builder()
                .operational(false)
                .build();
        when(mockScreenRepository.save(com.bookmymovie.theater.entity.Screen.builder()
                .operational(false)
                .build())).thenReturn(screen1);

        when(mockScreenConverter.convertEntityToModel(com.bookmymovie.theater.entity.Screen.builder()
                .operational(false)
                .build())).thenThrow(CoversionException.class);
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.RECORD_NOT_FOUND_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final ScreenResponse result = screenServiceUnderTest.updateScreenOperational(screenRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
