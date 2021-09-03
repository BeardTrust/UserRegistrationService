package com.beardtrust.webapp.userservice.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * This is the abstract base class for all transaction classes.  It contains
 * a UUID-based transactionId, a
 *
 * @author Matthew Crowell <Matthew.Crowell@Smoothstack.com>
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class FinancialTransaction implements Comparable<FinancialTransaction>, Serializable {
	private static final long serialVersionUID = 7424732886566449898L;

	@Id
	private String transactionId;
	@ManyToOne
	private TransactionType transactionType;
	@Embedded
	CurrencyValue transactionAmount;
	@ManyToOne
	TransactionStatus transactionStatus;
	@ManyToOne
	private FinancialAsset source;
	@ManyToOne
	private FinancialAsset target;
	private LocalDateTime statusTime;
	private String notes;

	/**
	 * Gets transaction id.
	 *
	 * @return the transaction id
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * Sets transaction id.
	 *
	 * @param transactionId the transaction id
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * Gets transaction type.
	 *
	 * @return the transaction type
	 */
	public TransactionType getTransactionType() {
		return transactionType;
	}

	/**
	 * Sets transaction type.
	 *
	 * @param transactionType the transaction type
	 */
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * Gets transaction amount.
	 *
	 * @return the transaction amount
	 */
	public CurrencyValue getTransactionAmount() {
		return transactionAmount;
	}

	/**
	 * Sets transaction amount.
	 *
	 * @param transactionAmount the transaction amount
	 */
	public void setTransactionAmount(CurrencyValue transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	/**
	 * Gets transaction status.
	 *
	 * @return the transaction status
	 */
	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	/**
	 * Sets transaction status.
	 *
	 * @param transactionStatus the transaction status
	 */
	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	/**
	 * Gets source.
	 *
	 * @return the source
	 */
	public FinancialAsset getSource() {
		return source;
	}

	/**
	 * Sets source.
	 *
	 * @param source the source
	 */
	public void setSource(FinancialAsset source) {
		this.source = source;
	}

	/**
	 * Gets target.
	 *
	 * @return the target
	 */
	public FinancialAsset getTarget() {
		return target;
	}

	/**
	 * Sets target.
	 *
	 * @param target the target
	 */
	public void setTarget(FinancialAsset target) {
		this.target = target;
	}

	/**
	 * Gets status time.
	 *
	 * @return the status time
	 */
	public LocalDateTime getStatusTime() {
		return statusTime;
	}

	/**
	 * Sets status time.
	 *
	 * @param statusTime the status time
	 */
	public void setStatusTime(LocalDateTime statusTime) {
		this.statusTime = statusTime;
	}

	/**
	 * Gets notes.
	 *
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Sets notes.
	 *
	 * @param notes the notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FinancialTransaction that = (FinancialTransaction) o;
		return Objects.equals(transactionId, that.transactionId) && Objects.equals(transactionType, that.transactionType) && Objects.equals(transactionAmount, that.transactionAmount) && Objects.equals(transactionStatus, that.transactionStatus) && Objects.equals(source, that.source) && Objects.equals(target, that.target) && Objects.equals(statusTime, that.statusTime) && Objects.equals(notes, that.notes);
	}

	@Override
	public int hashCode() {
		return Objects.hash(transactionId, transactionType, transactionAmount, transactionStatus, source, target, statusTime, notes);
	}

	@Override
	public String toString() {
		return "FinancialTransaction{" +
				"transactionId='" + transactionId + '\'' +
				", transactionType=" + transactionType +
				", transactionAmount=" + transactionAmount +
				", transactionStatus=" + transactionStatus +
				", source=" + source +
				", target=" + target +
				", statusTime=" + statusTime +
				", notes='" + notes + '\'' +
				'}';
	}

	@Override
	public int compareTo(FinancialTransaction o) {
		return this.transactionAmount.compareTo(o.transactionAmount);
	}
}
