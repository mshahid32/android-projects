package com.example.easytravel.Models

class Users {
    var profilepic: String? = null
    var userName: String? = null
    var mail: String? = null
    var password: String? = null
    var userId: String? = null
    var contact: String? = null
    var payment: String? = null

    constructor(
        profilepic: String?,
        userName: String?,
        mail: String?,
        password: String?,
        userId: String?,
        lastMessage: String?
    ) {
        this.profilepic = profilepic
        this.userName = userName
        this.mail = mail
        this.password = password
        this.userId = userId
        contact = contact
        payment = payment
    }

    constructor() {}

    //Signup constrater
    constructor(
        userName: String?,
        contact: String?,
        mail: String?,
        password: String?,
        payment: String?
    ) {
        this.userName = userName
        this.contact = contact
        this.mail = mail
        this.password = password
        this.payment = payment
    }
}
