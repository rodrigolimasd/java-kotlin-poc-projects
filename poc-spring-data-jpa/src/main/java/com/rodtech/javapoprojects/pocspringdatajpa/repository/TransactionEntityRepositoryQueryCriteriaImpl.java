package com.rodtech.javapoprojects.pocspringdatajpa.repository;

import com.rodtech.javapoprojects.pocspringdatajpa.dto.TransactionFilterDTO;
import com.rodtech.javapoprojects.pocspringdatajpa.model.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionEntityRepositoryQueryCriteriaImpl implements TransactionEntityRepositoryQueryCriteria {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<TransactionEntity> filter(TransactionFilterDTO filter, Pageable page) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<TransactionEntity> criteria = builder.createQuery(TransactionEntity.class);
        Root<TransactionEntity> root = criteria.from(TransactionEntity.class);

        Predicate[] predicates = applyCriteria(filter, builder, root);
        criteria.where(predicates);
        criteria.orderBy(builder.asc(root.get("date")));

        TypedQuery<TransactionEntity> query = manager.createQuery(criteria);

        if(!page.isUnpaged()) {
            pageRestrictions(query, page);
        }
        return new PageImpl<>(query.getResultList(), page, totalOfItems(filter));
    }

    private Predicate[] applyCriteria(
            TransactionFilterDTO filter, CriteriaBuilder builder, Root<TransactionEntity> root){

        List<Predicate> predicates = new ArrayList<>();

        if(filter.getDate()!=null){
            predicates.add(builder.equal(root.get("date"), filter.getDate()));
        }

        if(filter.getDate()==null && filter.getStartDate()!=null){
            predicates.add(builder.greaterThanOrEqualTo(root.get("date"), filter.getStartDate()));
        }
        if(filter.getDate()==null && filter.getEndDate()!=null){
            predicates.add(builder.lessThanOrEqualTo(root.get("date"), filter.getEndDate()));
        }

        if(filter.getIncome()!=null){
            predicates.add(builder.equal(root.get("income"), filter.getIncome()));
        }
        if(filter.getIncome()==null && filter.getIncomeFrom()!=null){
            predicates.add(builder.greaterThanOrEqualTo(root.get("income"), filter.getIncomeFrom()));
        }
        if(filter.getIncome()==null && filter.getIncomeUpTo()!=null){
            predicates.add(builder.lessThanOrEqualTo(root.get("income"), filter.getIncomeUpTo()));
        }

        if(filter.getExpense()!=null){
            predicates.add(builder.equal(root.get("expense"), filter.getExpense()));
        }
        if(filter.getExpense()==null && filter.getExpenseFrom()!=null){
            predicates.add(builder.greaterThanOrEqualTo(root.get("expense"), filter.getExpenseFrom()));
        }
        if(filter.getExpense()==null && filter.getExpenseUpTo()!=null){
            predicates.add(builder.lessThanOrEqualTo(root.get("expense"), filter.getExpenseUpTo()));
        }

        if(filter.getBalanceValue()!=null){
            predicates.add(builder.equal(root.get("balanceValue"), filter.getBalanceValue()));
        }
        if(filter.getBalanceValue()==null && filter.getBalanceValueFrom()!=null){
            predicates.add(builder.greaterThanOrEqualTo(root.get("balanceValue"), filter.getBalanceValueFrom()));
        }
        if(filter.getBalanceValue()==null && filter.getBalanceValueUpTo()!=null){
            predicates.add(builder.lessThanOrEqualTo(root.get("balanceValue"), filter.getBalanceValueUpTo()));
        }

        if(filter.getNote()!=null && !filter.getNote().isEmpty()){
            predicates.add(builder.equal(root.get("note"), filter.getNote()));
        }
        if(filter.getNote()==null && filter.getNoteContains()!=null && !filter.getNoteContains().isEmpty()){
            predicates.add(builder.like(builder.lower(root.get("note")), "%" + filter.getNoteContains().toUpperCase() + "%"));
        }

        return predicates.toArray(new Predicate[0]);
    }

    private void pageRestrictions(TypedQuery<?> query, Pageable pageable) {
        int currentPage = pageable.getPageNumber();
        int totalItendByPage = pageable.getPageSize();
        int firstItemPage = currentPage * totalItendByPage;

        query.setFirstResult(firstItemPage);
        query.setMaxResults(totalItendByPage);

    }

    private Long totalOfItems(TransactionFilterDTO filter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<TransactionEntity> root = criteria.from(TransactionEntity.class);

        Predicate[] predicates = applyCriteria(filter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }
}
