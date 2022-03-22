import Geohash from 'latlon-geohash'

export default {
  deduceLocation (place) {
    const address = {}
    const addressComponents = place.address_components
    address.formatted_address = place.formatted_address
    if (addressComponents !== null && addressComponents !== undefined) {
      addressComponents.forEach((mergeData) => {
        mergeData.types.forEach((data) => {
          if (data === 'sublocality_level_2') {
            address.sublocality_level_2 = mergeData.long_name
          } else if (data === 'sublocality_level_1') {
            address.sublocality_level_1 = mergeData.long_name
          } else if (data === 'locality') {
            address.locality = mergeData.long_name
          } else if (data === 'administrative_area_level_1') {
            address.administrative_area_level_1 = mergeData.long_name
          } else if (data === 'country') {
            address.country = mergeData.long_name
          } else if (data === 'postal_code') {
            address.postal_code = mergeData.long_name
          }
        })
      })
      return address
    }
  },
  fromLatLongToAddress (latitude, longitude) {
    return new Promise((resolve, reject) => {
      const geohash150 = Geohash.encode(latitude, longitude, 3)
      const geohash50 = Geohash.encode(latitude, longitude, 4)
      const geohash5 = Geohash.encode(latitude, longitude, 5)
      const geohash1 = Geohash.encode(latitude, longitude, 6)
      const latlng = {
        lat: latitude,
        lng: longitude
      }
      this.$geocoder.send(latlng, (results, status) => {
        if (status === 'OK') {
          if (results[0]) {
            const location = this.deduceLocation(results[0])
            location.latitude = latitude
            location.longitude = longitude
            location.geohash150 = geohash150
            location.geohash50 = geohash50
            location.geohash5 = geohash5
            location.geohash1 = geohash1
            resolve({ data: location })
          } else {
            reject(new Error('No results found'))
          }
        } else {
          reject(new Error('Geocoder failed due to: ' + status))
        }
      })
    })
  },
  distanceBtwnTwoLatLng: function distance (lat1, lon1, lat2, lon2, unit) {
    const radlat1 = (Math.PI * lat1) / 180
    const radlat2 = (Math.PI * lat2) / 180
    const theta = lon1 - lon2
    const radtheta = (Math.PI * theta) / 180
    let dist =
      Math.sin(radlat1) * Math.sin(radlat2) +
      Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radtheta)
    dist = Math.acos(dist)
    dist = (dist * 180) / Math.PI
    dist = dist * 60 * 1.1515
    if (unit === 'K') {
      dist = dist * 1.609344
    }
    if (unit === 'N') {
      dist = dist * 0.8684
    }
    return dist
  },
  convertKmToUserReadableDist (dist) {
    if (dist > 1) {
      return Math.round(dist).toString() + ' Km'
    } else if (dist > 0.1) {
      return Math.round(dist * 1000).toString() + ' m'
    } else {
      return 'Nearby'
    }
  },
  getGeoHashValueMap () {
    const geoHashValueMap = new Map()
    geoHashValueMap.set('geohash1', '1 kms')
    geoHashValueMap.set('geohash5', '5 kms')
    geoHashValueMap.set('geohash50', '50 kms')
    geoHashValueMap.set('geohash150', '150 kms')
    return geoHashValueMap
  }
}
