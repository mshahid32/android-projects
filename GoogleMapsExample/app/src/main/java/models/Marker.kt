package models


class Marker {
    var latitude: String? = null
    var longitude: String? = null
    var title: String? = null
    var id: String? = null

    constructor() {}
    constructor(latitude: String?, longitude: String?, title: String?, id: String?) {
        this.latitude = latitude
        this.longitude = longitude
        this.title = title
        this.id = id
    }
}

