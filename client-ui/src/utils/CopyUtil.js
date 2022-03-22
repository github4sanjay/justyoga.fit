import { copyToClipboard } from "quasar";
import NotifyUtil from "./NotifyUtil";
export default {
  async copyToClipboard(val) {
    try {
      await copyToClipboard(val);
      NotifyUtil.showSuccess("Copied");
    } catch (e) {
      NotifyUtil.showError("Share copy not working");
    }
  }
};
