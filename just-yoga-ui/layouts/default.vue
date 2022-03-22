<template>
  <v-app>
    <core-toolbar />

    <core-drawer />

    <core-view />

    <core-footer />

<!--    <core-cta />-->

    <common-dialogue />
    <error-snackbar />

    <success-snackbar />
  </v-app>
</template>

<script>
import CommonDialogue from "../components/core/CommonDialogue";
import axios from "axios";
import ApiEndpoints from "../constants/ApiEndpoints";
export default {
  name: "App",
  middleware: "place",
  components: {
    CommonDialogue,
    CoreCta: () => import("@/components/core/Cta"),
    CoreDrawer: () => import("@/components/core/Drawer"),
    CoreFooter: () => import("@/components/core/Footer"),
    CoreToolbar: () => import("@/components/core/Toolbar"),
    CoreView: () => import("@/components/core/View"),
    ErrorSnackbar: () => import("@/components/core/ErrorSnackbar"),
    SuccessSnackbar: () => import("@/components/core/SuccessSnackbar"),
  },
  data() {
    return {};
  },
  async mounted() { // this logic is for redirecting user to their place page
    if (this.$route.path === "/") { // only for the home page
      await this.$store.dispatch("shared/setDialogText", "Customising experience");
      await this.$store.dispatch("shared/setDialog", true);
      try {
        const configData = {
          url: "https://api.ipify.org?format=json", // "http://ip-api.com/json",
          method: "get",
        };
        let response = await axios.request(configData);
        if (response && response.status === 200 && response.data) {
          const ipBody = {
            url: ApiEndpoints.PLACE_IDS,
            params: {
              ip: response.data.ip,
            },
            method: "get",
          };
          let ipResponse = await axios.request(ipBody);
          if (ipResponse && ipResponse.status === 200 && ipResponse.data) {
            let data = ipResponse.data.data;
            let redirectUrl = null;
            if (data.localityId) {
              redirectUrl = `/directory/${data.countryId}/${data.administrativeAreaLevel1Id}/${data.localityId}/trainer/0`;
            } else if (data.administrativeAreaLevel1Id) {
              redirectUrl = `/directory/${data.countryId}/${data.administrativeAreaLevel1Id}/trainer/0`;
            } else if (data.countryId) {
              redirectUrl = `/directory/${data.countryId}/trainer/0`;
            } else {
              redirectUrl = `/directory/trainer/0`;
            }
            if (redirectUrl) await this.$router.push(redirectUrl);
          }
        } else {
          console.log("error in getting ip");
        }
      } catch (e) {
        console.log("error in getting ip " + e);
      } finally {
        await this.$store.dispatch("shared/setDialogText", "");
        await this.$store.dispatch("shared/setDialog", false);
      }
    }
  },
};
</script>
