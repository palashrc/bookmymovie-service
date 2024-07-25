package com.bookmymovie.payment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NetBanking {

    private String bankName;

    private String netBankingId;

    private String netBankingPassword;
}
