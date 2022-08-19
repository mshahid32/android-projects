package com.example.easytravel.Models


class PaymentHistory {
    var date: String? = null
    var destination: String? = null
    var time: String? = null
    var price: String? = null
    var ticketType: String? = null

    constructor() {}
    constructor(
        date: String?,
        destination: String?,
        time: String?,
        price: String?,
        ticketType: String?
    ) {
        this.date = date
        this.destination = destination
        this.time = time
        this.price = price
        this.ticketType = ticketType
    }

}
