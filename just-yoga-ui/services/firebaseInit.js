import * as firebase from 'firebase/app'
import 'firebase/firestore'
import 'firebase/auth'
import firebaseConfig from './firebaseConfig'
const firebaseApp = !firebase.apps.length ? firebase.initializeApp(firebaseConfig) : ''
export const auth = firebase.auth()
export default firebaseApp.firestore
