import { Notify } from "quasar";
export default async ({ app, router, store, Vue }) => {
  // something to do
  try {
    await store.dispatch("place/getAll", {});
  } catch (error) {
    // notify
    Notify.create({
      color: "negative",
      message: "Danger, Will Robinson! Danger!"
    });
  }
};
