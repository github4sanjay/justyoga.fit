export default {
  getPlaceType(schoolType) {
    const PRIMARY_SCHOOL = 'Primary School' // 1
    const SECONDARY_SCHOOL = 'Secondary School' // 2
    const PRE_SCHOOL = 'Pre School' // 3
    const SPECIAL_SCHOOL = 'Special School' // 4
    const INTERNATIONAL_SCHOOL = 'International School' // 5
    const MUSIC_SCHOOL = 'Music Class' // 6
    const ART_SCHOOL = 'Art Class' // 7
    const SPORTS_SCHOOL = 'Sports Class' // 8
    const PRIVATE_TUTOR_SCHOOL = 'Private Tutor' // 9
    const COACHING_CLASS_SCHOOL = 'Coaching Class' // 10

    const placeType = {}

    schoolType.forEach((value) => {
      if (value === PRIMARY_SCHOOL) {
        placeType.primarySchool = true
      } else if (value === SECONDARY_SCHOOL) {
        placeType.secondarySchool = true
      } else if (value === PRE_SCHOOL) {
        placeType.preSchool = true
      } else if (value === SPECIAL_SCHOOL) {
        placeType.specialSchool = true
      } else if (value === INTERNATIONAL_SCHOOL) {
        placeType.internationalSchool = true
      } else if (value === MUSIC_SCHOOL) {
        placeType.musicClass = true
      } else if (value === ART_SCHOOL) {
        placeType.artClass = true
      } else if (value === SPORTS_SCHOOL) {
        placeType.sportsClass = true
      } else if (value === PRIVATE_TUTOR_SCHOOL) {
        placeType.privateTutor = true
      } else if (value === COACHING_CLASS_SCHOOL) {
        placeType.coachingClass = true
      }
    })
    return placeType
  },
  filterPass(school, filterMap) {
    let countPassed = 0
    if (filterMap.size === 0) {
      return true
    }
    filterMap.forEach((value, key) => {
      if (key === 'categories') {
        if (this.filterUtil(school.categories, value)) {
          countPassed++
        }
      } else if (key === 'dancing') {
        if (this.filterUtil(school.dancing, value)) {
          countPassed++
        }
      } else if (key === 'singing') {
        if (this.filterUtil(school.singing, value)) {
          countPassed++
        }
      } else if (key === 'instruments') {
        if (this.filterUtil(school.instruments, value)) {
          countPassed++
        }
      } else if (key === 'classesType') {
        if (this.filterUtil(school.classesType, value)) {
          countPassed++
        }
      } else if (key === 'gender') {
        if (this.filterUtil(school.gender, value)) {
          countPassed++
        }
      } else if (key === 'extracurricular') {
        if (this.filterUtil(school.extracurricular, value)) {
          countPassed++
        }
      } else if (key === 'facilities') {
        if (this.filterUtil(school.facilities, value)) {
          countPassed++
        }
      } else if (key === 'boards') {
        if (this.filterUtil(school.boards, value)) {
          countPassed++
        }
      } else if (key === 'classes') {
        if (this.filterUtil(school.classes, value)) {
          countPassed++
        }
      }
    })

    return filterMap.size === countPassed
  },

  filterUtil(entityArray, filterArray) {
    let passed = false
    if (entityArray !== null && entityArray !== undefined) {
      for (let i = 0; i < filterArray.length; i++) {
        if (entityArray[filterArray[i]] !== undefined) {
          passed = true
          break
        }
      }
      return passed
    }
  },

  fillSportsCategories() {
    const sports = [
      'Basketball',
      'Football',
      'Archery',
      'Swimming',
      'Skeet Shooting',
      'Wrestling',
      'Pool Playing',
      'Synchronized Swimming',
      'Volleyball',
      'Badminton',
      'Baseball',
      'Boxing',
      'Canoeing',
      'Cycling',
      'Tennis',
      'Gymnastics',
      'Hockey',
      'Karate',
      'Hang Gliding',
      'Parachuting',
      'Water Skiing',
      'Down Hill Skiing',
      'Cross Country Skiing',
      'Water Polo',
      'Bowling',
      'Racket ball',
      'Darts',
      'Foosball',
      'Decathlon',
      'Hunting',
      'Speed Skating',
      'Figure Skating',
      'Handball',
      'Rowing',
      'Sailing',
      'Synchronized Swimming',
      'Table Tennis',
      'Triathlon',
      'Weight Lifting',
      'Crochet',
      'Horseshoes',
      'Bocce Ball',
      'Soccer',
      'Rugby',
      'Motorcycle Racing',
      'Automobile Racing',
      'Aquatics',
      'Archery',
      'Equestrian',
      'Fencing',
      'Judo',
      'Modern Pentathlon',
      'Rowing',
      'Taekwondo',
      'Biathlon',
      'Bobsleigh',
      'Curling',
      'Ice Hockey',
      'Luge',
      'Golf',
      'Roller Skating',
      'Surfing',
      'Scuba Diving',
      'Mountaineering and Climbing',
      'Squash',
      'Sumo Wrestling',
      'Wushu',
      'Chess',
      'Netball',
      'Kayaking',
      'Snowshoeing',
      'Mountain Biking',
      'Sprint Running',
      'Cross Country Running',
      'Power Walking',
      'Snow Sledding',
      'Paint ball',
      'Rock Climbing',
      'Hiking',
      'Roller Skating',
      'Ice Skating',
      'Fishing',
      'Water Tubing',
      'Boomerang',
      'Cricket',
      'Cheerleading',
      'Jai Alai',
      'Fencing',
      'Paddle Ball',
      'Lacrosse',
      'Pétanque',
      'Skateboarding',
      'Tchoukball',
      'Track and Field',
      'Bird Watching',
      'Horseback Riding',
      'Prospecting',
      'Snow Biking',
      'White Water Rafting',
      'Water Snorkling',
      'Dog Sledding',
      'Sport Fishing',
      'River Rafting',
      'Whale Watching',
      'Sky Diving',
      'Camping',
      'Inline Skating',
      'Metal Detecting',
      'Bull Fighting',
      'Falconry',
      'Dog Training',
      'Rodeo Riding',
      'Snow Boarding',
      'Shuffle Board',
      'Flag Football',
      'Fox Hunting',
      'Model Flying',
      'Remote Control Boating',
      'Medicine Ball',
      'Hot Air Ballooning',
      'Wheelchair Basketball',
      'Caving',
      'Diving',
      'Modern Dance',
      'Classical Dance',
      'Para Gliding',
      'Knee Boarding',
      'Yachting',
      'Land Sailing',
      'Jump Roping',
      'Sombo',
      'Tug of War',
      'Wind Surfing',
      'Yoga',
      'Stunt Plane Flying',
      'Train Collecting',
      'Biathlon',
      'Log Rolling',
      'Tree Topping',
      'Body Building',
      'High Jump',
      'Long Jump',
      'Snooker',
      'Shot Put'
    ]
  }
}
