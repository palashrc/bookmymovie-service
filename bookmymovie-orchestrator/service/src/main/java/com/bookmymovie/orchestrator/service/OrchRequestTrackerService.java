package com.bookmymovie.orchestrator.service;

import com.bookmymovie.core.util.CommonUtils;
import com.bookmymovie.orchestrator.entity.OrchRequestTracker;
import com.bookmymovie.orchestrator.model.BookingResponseAck;
import com.bookmymovie.orchestrator.repository.OrchRequestTrackerRepository;
import com.google.cloud.datastore.DatastoreException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class OrchRequestTrackerService {

    @Autowired
    private OrchRequestTrackerRepository orchRequestTrackerRepository;

    public void createTracker(String txnId) {
        try {
            OrchRequestTracker orchRequestTracker = new OrchRequestTracker();
            orchRequestTracker.setTransactionId(txnId);
            orchRequestTracker.setReqTimeStamp(CommonUtils.getTimeStamp());
            orchRequestTracker.setTxnStatus(TxnStatus.RECEIVED.toString());
            orchRequestTrackerRepository.save(orchRequestTracker);
        } catch(DatastoreException ex) {
            log.error("DatastoreException Occurs!");
            ex.printStackTrace();
        } catch(Exception ex) {
            log.error("Exception Occurs!");
            ex.printStackTrace();
        }
    }

    public void updateTrackerForInterimRes(String txnId, BookingResponseAck ack) {
        try {
            OrchRequestTracker orchRequestTracker =  orchRequestTrackerRepository.findByTransactionId(txnId).get();
            orchRequestTracker.setResInterimTimeStamp(CommonUtils.getTimeStamp());
            orchRequestTracker.setTxnStatus(CollectionUtils.isEmpty(ack.getErrors()) ? TxnStatus.ACKNOWLEDGED.toString() : TxnStatus.IGNORED.toString());
            orchRequestTrackerRepository.save(orchRequestTracker);
        } catch(DatastoreException ex) {
            log.error("DatastoreException Occurs!");
            ex.printStackTrace();
        } catch(Exception ex) {
            log.error("Exception Occurs!");
            ex.printStackTrace();
        }
    }

    public void updateTrackerForFinalRes(String txnId) {
        try {
            OrchRequestTracker orchRequestTracker =  orchRequestTrackerRepository.findByTransactionId(txnId).get();
            orchRequestTracker.setResInterimTimeStamp(CommonUtils.getTimeStamp());
            orchRequestTracker.setTxnStatus(TxnStatus.COMPLETED.toString());
            orchRequestTrackerRepository.save(orchRequestTracker);
        } catch(DatastoreException ex) {
            log.error("DatastoreException Occurs!");
            ex.printStackTrace();
        } catch(Exception ex) {
            log.error("Exception Occurs!");
            ex.printStackTrace();
        }
    }

    enum TxnStatus {
        RECEIVED,
        ACKNOWLEDGED,
        IGNORED,
        COMPLETED
    }
}
