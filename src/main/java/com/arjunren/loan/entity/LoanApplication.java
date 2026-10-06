package com.arjunren.loan.entity;

import com.arjunren.loan.domain.LoanStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity @Table(name="loan_applications",indexes={@Index(name="loans_customer_idx",columnList="customer_id"),@Index(name="loans_status_idx",columnList="status")})
public class LoanApplication {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="customer_id") private AppUser customer;
    @Column(nullable=false,precision=19,scale=2) private BigDecimal principal;
    @Column(name="term_months",nullable=false) private int termMonths;
    @Column(nullable=false,length=500) private String purpose;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private LoanStatus status=LoanStatus.PENDING;
    @Column(name="annual_interest_rate",precision=7,scale=4) private BigDecimal annualInterestRate;
    @Column(name="total_repayable",precision=19,scale=2) private BigDecimal totalRepayable;
    @Column(name="outstanding_balance",precision=19,scale=2) private BigDecimal outstandingBalance;
    @Column(name="rejection_reason",length=500) private String rejectionReason;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="reviewed_by") private AppUser reviewedBy;
    @Column(name="decided_at") private Instant decidedAt;
    @Version private long version;
    @Column(name="created_at",nullable=false,updatable=false) private Instant createdAt=Instant.now();
    @Column(name="updated_at",nullable=false) private Instant updatedAt=Instant.now();
    protected LoanApplication(){}
    public LoanApplication(AppUser customer,BigDecimal principal,int termMonths,String purpose){this.customer=customer;this.principal=principal;this.termMonths=termMonths;this.purpose=purpose;}
    @PreUpdate void timestamp(){updatedAt=Instant.now();}
    public void approve(BigDecimal rate,BigDecimal total,AppUser officer){status=LoanStatus.APPROVED;annualInterestRate=rate;totalRepayable=total;outstandingBalance=total;reviewedBy=officer;decidedAt=Instant.now();}
    public void reject(String reason,AppUser officer){status=LoanStatus.REJECTED;rejectionReason=reason;reviewedBy=officer;decidedAt=Instant.now();}
    public void disburse(){status=LoanStatus.DISBURSED;} public void repay(BigDecimal amount){outstandingBalance=outstandingBalance.subtract(amount);if(outstandingBalance.signum()==0)status=LoanStatus.PAID;}
    public Long getId(){return id;} public AppUser getCustomer(){return customer;} public BigDecimal getPrincipal(){return principal;} public int getTermMonths(){return termMonths;} public String getPurpose(){return purpose;} public LoanStatus getStatus(){return status;} public BigDecimal getAnnualInterestRate(){return annualInterestRate;} public BigDecimal getTotalRepayable(){return totalRepayable;} public BigDecimal getOutstandingBalance(){return outstandingBalance;} public String getRejectionReason(){return rejectionReason;} public Instant getCreatedAt(){return createdAt;} public Instant getUpdatedAt(){return updatedAt;}
}

