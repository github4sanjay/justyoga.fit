<template>
  <div>
    <div class="row">
      <div class="col-12 q-py-sm">
        <div class="row justify-between items-center">
          <div
            :class="{
              'col-12': true,
              'col-sm-6': true,
              'q-pl-sm': $q.screen.gt.xs,
              'text-center': $q.screen.xs
            }"
          >
            Connect with us on social networks or call on +918882904882
          </div>
          <div
            :class="{
              'col-12': true,
              'col-sm-6': true,
              'text-right': $q.screen.gt.xs,
              'text-center q-mt-sm': $q.screen.xs
            }"
          >
            <q-btn
              v-for="(item, index) in items"
              :key="index"
              stretch
              flat
              router
              :icon="item.icon"
              :label="$q.screen.gt.sm ? item.label : ''"
              type="a"
              target="_blank"
              :href="item.href"
            />
          </div>
        </div>
      </div>

      <div class="col-12">
        <q-banner dense inline-actions class="text-white bg-blue-grey-9">
          <div
            :class="{
              'row justify-between items-center': $q.screen.gt.xs,
              'row text-center items-center': $q.screen.xs
            }"
          >
            <div class="col-12 col-sm-4">
              <q-btn
                no-caps
                label="Read About Tech"
                stretch
                flat
                router
                to="/tech"
              />
              <q-btn
                no-caps
                label="Credits"
                stretch
                flat
                @click="creditDialogue = true"
              />
            </div>
            <div
              :class="{
                'col-12 col-sm-4 text-right': $q.screen.gt.xs,
                'col-12 col-sm-4 q-mt-sm': $q.screen.xs
              }"
            >
              Copyright © 2020 JustYoga
            </div>
          </div>
        </q-banner>
      </div>
    </div>
    <q-dialog v-model="creditDialogue">
      <q-card
        flat
        :style="{
          'min-width': $q.screen.gt.sm ? '600px' : ''
        }"
      >
        <q-toolbar class="bg-primary text-white">
          <q-toolbar-title>Credit Details</q-toolbar-title>
          <q-btn flat round icon="close" v-close-popup />
        </q-toolbar>

        <q-card-section class="q-pt-none text-center text-bold q-mt-md">
          <q-list style="min-width: 100px">
            <q-item clickable v-close-popup>
              <q-item-section>
                <div>
                  Server funded by
                  <a href="https://aws.amazon.com/"> AWS</a>
                </div>
              </q-item-section>
            </q-item>
            <q-separator />
            <q-item clickable v-close-popup>
              <q-item-section>
                <div>
                  Icons made by
                  <a
                    href="https://www.flaticon.com/authors/vitaly-gorbachev"
                    title="Vitaly Gorbachev"
                    >Vitaly Gorbachev</a
                  >
                  from
                  <a href="https://www.flaticon.com/" title="Flaticon"
                    >www.flaticon.com</a
                  >
                </div>
              </q-item-section>
            </q-item>
            <q-item clickable v-close-popup>
              <q-item-section
                ><a href="https://www.freepik.com/free-photos-vectors/sport"
                  >Sport vector created by freepik - www.freepik.com</a
                ></q-item-section
              >
            </q-item>
            <q-item clickable v-close-popup>
              <q-item-section
                ><a href="https://www.freepik.com/free-photos-vectors/woman"
                  >Woman vector created by macrovector - www.freepik.com</a
                ></q-item-section
              >
            </q-item>
          </q-list>
        </q-card-section>
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
export default {
  data: () => ({
    creditDialogue: false,
    items: [
      {
        href: "tel:+918882904882",
        icon: "fas fa-phone",
        label: "Phone"
      },
      {
        href: "https://twitter.com/fit_justyoga",
        icon: "fab fa-twitter",
        label: "Twitter"
      },
      {
        href: "https://www.instagram.com/justyoga.fit/",
        icon: "fab fa-instagram",
        label: "Instagram"
      },
      {
        href: "https://www.facebook.com/fit.justyoga",
        icon: "fab fa-facebook",
        label: "Facebook"
      }
    ]
  })
};
</script>
