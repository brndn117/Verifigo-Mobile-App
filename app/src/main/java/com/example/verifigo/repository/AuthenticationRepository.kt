package com.example.verifigo.repository

import com.example.verifigo.dao.AuthenticationReportDao // Need this import
import com.example.verifigo.dao.BuyerDao
import com.example.verifigo.dao.SellerDao
import com.example.verifigo.model.AuthenticationReport // Need this import
import com.example.verifigo.model.Buyer
import com.example.verifigo.model.Seller

class AuthenticationRepository(
    private val buyerDao: BuyerDao,
    private val sellerDao: SellerDao,
    // FIX 1: Add the third DAO to the constructor
    private val authenticationReportDao: AuthenticationReportDao
) {
    // Existing functions...
    suspend fun registerBuyer(buyer: Buyer) = buyerDao.registerBuyer(buyer)
    suspend fun loginBuyer(email: String, password: String): Buyer? = buyerDao.login(email, password)
    suspend fun registerSeller(seller: Seller) = sellerDao.registerSeller(seller)
    suspend fun loginSeller(email: String, password: String): Seller? = sellerDao.login(email, password)

    // FIX 2: Add the insertReport function
    suspend fun insertReport(report: AuthenticationReport) = authenticationReportDao.insertReport(report)
}