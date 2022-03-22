// import axios from "axios";
// import Geohash from "latlon-geohash";
// import LocationUtil from "../utils/LocationUtil";

// export default context => {
//   const { store } = context;
//   return new Promise((resolve, reject) => {
//     const location = store.getters["location/location"];
//     console.log("location plugin already saved location==>", location);
//     if (location === null || location === undefined) {
//       axios
//         .get(`http://ip-api.com/json`)
//         .then(res => {
//           store.dispatch("location/setIpLocation", res.data);
//           const geohash150 = Geohash.encode(res.data.lat, res.data.lon, 3);
//           const geohash50 = Geohash.encode(res.data.lat, res.data.lon, 4);
//           const geohash5 = Geohash.encode(res.data.lat, res.data.lon, 5);
//           const geohash1 = Geohash.encode(res.data.lat, res.data.lon, 6);

//           const url =
//             "https://maps.googleapis.com/maps/api/geocode/json?latlng=" +
//             res.data.lat +
//             "," +
//             res.data.lon +
//             "&key=AIzaSyCg9KqjmKWZOFws3MUGPdx4uPJgjlndwds";
//           axios
//             .get(url)
//             .then(loc => {
//               let results = loc.data.results;
//               if (results[0]) {
//                 let location = LocationUtil.deduceLocation(results[0]);
//                 location.latitude = res.data.lat;
//                 location.longitude = res.data.lon;
//                 location.geohash150 = geohash150;
//                 location.geohash50 = geohash50;
//                 location.geohash5 = geohash5;
//                 location.geohash1 = geohash1;
//                 console.log(location);
//                 store.dispatch("location/setLocation", location);
//                 resolve({ data: location });
//               } else {
//                 resolve({ data: "No results found" });
//               }
//             })
//             .catch(e => {
//               resolve({ data: "Geocoder failed due to: " + e });
//             });
//         })
//         .catch(e => {
//           resolve({ statusCode: 404, data: "Ip location not found" + e });
//         });
//     }
//   });
// };
