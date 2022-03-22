export default {
  /**
   * Validate email id
   * @return {boolean}
   */
  validateEmail(mail) {
    return /^\w+([-]?\w+)*@\w+([-]?\w+)*(\.\w{2,3})+$/.test(mail);
  },
  /**
   * Validate phone number
   * @return {boolean}
   */
  validatePhoneNumber(inputText) {
    const phoneNo = /^\d{10}$/;
    return !!inputText.match(phoneNo);
  },
  isUrl(str) {
    const regexp = /^(?:(?:https?|ftp):\/\/)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00A1-\uFFFF0-9]-*)*[a-z\u00A1-\uFFFF0-9]+)(?:\.(?:[a-z\u00A1-\uFFFF0-9]-*)*[a-z\u00A1-\uFFFF0-9]+)*(?:\.(?:[a-z\u00A1-\uffff]{2,})))(?::\d{2,5})?(?:\/\S*)?$/;
    return regexp.test(str);
  },
  listToObject(list) {
    const map = {};
    list.forEach((value) => {
      map[value] = true;
    });
    return map;
  },
  isObjectEmpty(obj) {
    for (const key in obj) {
      if (obj.hasOwnProperty(key)) return false;
    }
    return true;
  },
  getRandomColor() {
    const letters = "0123456789ABCDEF";
    let color = "#";
    for (let i = 0; i < 6; i++) {
      color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
  },
  getValueFromObjectByString(obj, is, value) {
    // console.log("obj ==>",obj)
    // console.log("is ==>",is)
    // console.log("value ==>",value)
    if (typeof is === "string")
      return this.getValueFromObjectByString(obj, is.split("."), value);
    else if (is.length == 1 && value !== undefined) return (obj[is[0]] = value);
    else if (is.length == 0) return obj;
    else return this.getValueFromObjectByString(obj[is[0]], is.slice(1), value);
  },
};
