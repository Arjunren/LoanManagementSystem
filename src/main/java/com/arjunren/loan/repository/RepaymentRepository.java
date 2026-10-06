package com.arjunren.loan.repository;
import com.arjunren.loan.entity.Repayment; import org.springframework.data.domain.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface RepaymentRepository extends JpaRepository<Repayment,Long>{Page<Repayment> findByLoanId(Long loanId,Pageable pageable);}

