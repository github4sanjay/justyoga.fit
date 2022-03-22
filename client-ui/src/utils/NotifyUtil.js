import { Notify } from "quasar";

export default {
  showError(msg) {
    Notify.create({
      color: "negative",
      message: msg
    });
  },
  showSuccess(msg) {
    Notify.create({
      color: "positive",
      message: msg
    });
  },
  showWarning(msg) {
    Notify.create({
      color: "warning",
      message: msg
    });
  },
  showInfo(msg) {
    Notify.create({
      color: "info",
      message: msg
    });
  }
};
