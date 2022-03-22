import TimeAgo from "javascript-time-ago";
import en from "javascript-time-ago/locale/en";

export default {
  timeDifference(dateStart) {
    if (!dateStart) return "NA";
    const dateTime = dateStart.replace(/([+\-]\d\d)(\d\d)$/, "$1:$2");
    TimeAgo.addLocale(en);
    const timeAgo = new TimeAgo("en-US");
    return timeAgo.format(new Date(dateTime));
  }
};
