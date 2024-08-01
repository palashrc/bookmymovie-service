package com.bookmymovie.viewer.service;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.core.error.Error;
import com.bookmymovie.viewer.constant.ExceptionConstants;
import com.bookmymovie.viewer.converter.ViewerConverter;
import com.bookmymovie.viewer.helper.StatusMapper;
import com.bookmymovie.viewer.model.Viewer;
import com.bookmymovie.viewer.model.ViewerRequest;
import com.bookmymovie.viewer.model.ViewerResponse;
import com.bookmymovie.viewer.repository.ViewerRepository;
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
class ViewerServiceTest {

    @Mock
    private ViewerConverter mockViewerConverter;
    @Mock
    private ViewerRepository mockViewerRepository;
    @Mock
    private StatusMapper mockStatusMapper;

    @InjectMocks
    private ViewerService viewerServiceUnderTest;

    @Test
    void testCreateViewer() throws Exception {
        // Setup
        final ViewerRequest viewerRequest = ViewerRequest.builder()
                .viewer(Viewer.builder()
                        .mobile("mobile")
                        .build())
                .build();
        final ViewerResponse expectedResult = ViewerResponse.builder()
                .viewers(List.of(Viewer.builder()
                        .mobile("mobile")
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockViewerRepository.findByMobile("mobile"))
                .thenReturn(Optional.of(com.bookmymovie.viewer.entity.Viewer.builder().build()));

        // Configure ViewerConverter.convertEntityToModel(...).
        final Viewer viewer = Viewer.builder()
                .mobile("mobile")
                .build();
        when(mockViewerConverter.convertEntityToModel(
                com.bookmymovie.viewer.entity.Viewer.builder().build())).thenReturn(viewer);

        when(mockViewerConverter.convertModelToEntity(Viewer.builder()
                .mobile("mobile")
                .build())).thenReturn(com.bookmymovie.viewer.entity.Viewer.builder().build());
        when(mockViewerRepository.save(com.bookmymovie.viewer.entity.Viewer.builder().build()))
                .thenReturn(com.bookmymovie.viewer.entity.Viewer.builder().build());

        // Run the test
        final ViewerResponse result = viewerServiceUnderTest.createViewer(viewerRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(ViewerResponse.builder()
                .viewers(List.of(Viewer.builder()
                        .mobile("mobile")
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testCreateViewer_ViewerRepositoryFindByMobileReturnsAbsent() throws Exception {
        // Setup
        final ViewerRequest viewerRequest = ViewerRequest.builder()
                .viewer(Viewer.builder()
                        .mobile("mobile")
                        .build())
                .build();
        final ViewerResponse expectedResult = ViewerResponse.builder()
                .viewers(List.of(Viewer.builder()
                        .mobile("mobile")
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockViewerRepository.findByMobile("mobile")).thenReturn(Optional.empty());

        // Configure ViewerConverter.convertEntityToModel(...).
        final Viewer viewer = Viewer.builder()
                .mobile("mobile")
                .build();
        when(mockViewerConverter.convertEntityToModel(
                com.bookmymovie.viewer.entity.Viewer.builder().build())).thenReturn(viewer);

        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());
        when(mockViewerConverter.convertModelToEntity(Viewer.builder()
                .mobile("mobile")
                .build())).thenReturn(com.bookmymovie.viewer.entity.Viewer.builder().build());
        when(mockViewerRepository.save(com.bookmymovie.viewer.entity.Viewer.builder().build()))
                .thenReturn(com.bookmymovie.viewer.entity.Viewer.builder().build());

        // Run the test
        final ViewerResponse result = viewerServiceUnderTest.createViewer(viewerRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(ViewerResponse.builder()
                .viewers(List.of(Viewer.builder()
                        .mobile("mobile")
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testCreateViewer_ViewerConverterConvertEntityToModelThrowsCoversionException() throws Exception {
        // Setup
        final ViewerRequest viewerRequest = ViewerRequest.builder()
                .viewer(Viewer.builder()
                        .mobile("mobile")
                        .build())
                .build();
        final ViewerResponse expectedResult = ViewerResponse.builder()
                .viewers(List.of(Viewer.builder()
                        .mobile("mobile")
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockViewerRepository.findByMobile("mobile"))
                .thenReturn(Optional.of(com.bookmymovie.viewer.entity.Viewer.builder().build()));
        when(mockViewerConverter.convertEntityToModel(
                com.bookmymovie.viewer.entity.Viewer.builder().build())).thenThrow(CoversionException.class);
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());
        when(mockViewerConverter.convertModelToEntity(Viewer.builder()
                .mobile("mobile")
                .build())).thenReturn(com.bookmymovie.viewer.entity.Viewer.builder().build());
        when(mockViewerRepository.save(com.bookmymovie.viewer.entity.Viewer.builder().build()))
                .thenReturn(com.bookmymovie.viewer.entity.Viewer.builder().build());

        // Run the test
        final ViewerResponse result = viewerServiceUnderTest.createViewer(viewerRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(ViewerResponse.builder()
                .viewers(List.of(Viewer.builder()
                        .mobile("mobile")
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testCreateViewer_ViewerConverterConvertModelToEntityThrowsCoversionException() throws Exception {
        // Setup
        final ViewerRequest viewerRequest = ViewerRequest.builder()
                .viewer(Viewer.builder()
                        .mobile("mobile")
                        .build())
                .build();
        final ViewerResponse expectedResult = ViewerResponse.builder()
                .viewers(List.of(Viewer.builder()
                        .mobile("mobile")
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockViewerRepository.findByMobile("mobile"))
                .thenReturn(Optional.of(com.bookmymovie.viewer.entity.Viewer.builder().build()));

        // Configure ViewerConverter.convertEntityToModel(...).
        final Viewer viewer = Viewer.builder()
                .mobile("mobile")
                .build();
        when(mockViewerConverter.convertEntityToModel(
                com.bookmymovie.viewer.entity.Viewer.builder().build())).thenReturn(viewer);

        when(mockViewerConverter.convertModelToEntity(Viewer.builder()
                .mobile("mobile")
                .build())).thenThrow(CoversionException.class);
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final ViewerResponse result = viewerServiceUnderTest.createViewer(viewerRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(ViewerResponse.builder()
                .viewers(List.of(Viewer.builder()
                        .mobile("mobile")
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testGetViewerByMobile() throws Exception {
        // Setup
        final ViewerRequest viewerRequest = ViewerRequest.builder()
                .viewer(Viewer.builder()
                        .mobile("mobile")
                        .build())
                .build();
        final ViewerResponse expectedResult = ViewerResponse.builder()
                .viewers(List.of(Viewer.builder()
                        .mobile("mobile")
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockViewerRepository.findByMobile("mobile"))
                .thenReturn(Optional.of(com.bookmymovie.viewer.entity.Viewer.builder().build()));

        // Configure ViewerConverter.convertEntityToModel(...).
        final Viewer viewer = Viewer.builder()
                .mobile("mobile")
                .build();
        when(mockViewerConverter.convertEntityToModel(
                com.bookmymovie.viewer.entity.Viewer.builder().build())).thenReturn(viewer);

        // Run the test
        final ViewerResponse result = viewerServiceUnderTest.getViewerByMobile(viewerRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(ViewerResponse.builder()
                .viewers(List.of(Viewer.builder()
                        .mobile("mobile")
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testGetViewerByMobile_ViewerRepositoryReturnsAbsent() {
        // Setup
        final ViewerRequest viewerRequest = ViewerRequest.builder()
                .viewer(Viewer.builder()
                        .mobile("mobile")
                        .build())
                .build();
        final ViewerResponse expectedResult = ViewerResponse.builder()
                .viewers(List.of(Viewer.builder()
                        .mobile("mobile")
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockViewerRepository.findByMobile("mobile")).thenReturn(Optional.empty());
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final ViewerResponse result = viewerServiceUnderTest.getViewerByMobile(viewerRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetViewerByMobile_ViewerConverterThrowsCoversionException() throws Exception {
        // Setup
        final ViewerRequest viewerRequest = ViewerRequest.builder()
                .viewer(Viewer.builder()
                        .mobile("mobile")
                        .build())
                .build();
        final ViewerResponse expectedResult = ViewerResponse.builder()
                .viewers(List.of(Viewer.builder()
                        .mobile("mobile")
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockViewerRepository.findByMobile("mobile"))
                .thenReturn(Optional.of(com.bookmymovie.viewer.entity.Viewer.builder().build()));
        when(mockViewerConverter.convertEntityToModel(
                com.bookmymovie.viewer.entity.Viewer.builder().build())).thenThrow(CoversionException.class);
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final ViewerResponse result = viewerServiceUnderTest.getViewerByMobile(viewerRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
