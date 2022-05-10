<template>
  <div class="row">
    <div class="col-2"></div>
    <div class="col-8 pt-5">
      <div class="container-fluid">
        <div class="align-items-center">
          <div class="row main-col align-items-end header rounded mb-1">
            <div class="col-sm-4 main-col">
              <h3>Name</h3>
            </div>
            <div class="col-sm-4 main-col">
              <h3>Place</h3>
            </div>
            <div class="col-sm-2 main-col">
              <h3>Duration</h3>
            </div>
            <div class="col-sm-2 main-col"></div>
          </div>
          <!-- Reviews listing -->
          <FishingInstructorAdventureView v-for="(adventure, index) in this.adventures" v-on:requestManaged="filterRequests()" :key="index" :adventure="adventure"/>
          <!-- Pagination -->
          <ul class="pagination justify-content-center">
            <li class="page-item" v-if="numberOfPages>2 && currentPage>1">
              <button class="page-link" @click="firstPage">
                <font-awesome-icon icon="angle-double-left"/>
              </button>
            </li>
            <li class="page-item" v-if="numberOfPages>0 && currentPage>0">
              <button class="page-link" @click="prevPage">
                <font-awesome-icon icon="angle-left"/>
              </button>
            </li>
            <li class="page-item">
              <h4 v-if="!adventures" class="no-result"> NO RESULTS!<font-awesome-icon icon="frown"/></h4>
              <h4 v-else-if="numberOfPages!==1">{{currentPage+1}}/{{numberOfPages}}</h4>
              <h4 v-else>{{currentPage+1}}</h4>
            </li>
            <li class="page-item" v-if="numberOfPages>0 && currentPage+1!==numberOfPages">
              <button class="page-link" @click="nextPage">
                <font-awesome-icon icon="angle-right"/>
              </button>
            </li>
            <li class="page-item" v-if="numberOfPages>0 && currentPage<numberOfPages-2">
              <button class="page-link" @click="lastPage">
                <font-awesome-icon icon="angle-double-right"/>
              </button>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div class="col-2"></div>
  </div>
</template>

<script>
import FishingInstructorAdventureView
  from "@/components/FishingInstructor/FishingInstructorPage/components/InstructorMainViews/InstructorAllAdventuresView/FishingInstructorAdventureView/FishingInstructorAdventureView";
import axios from "axios";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {library} from "@fortawesome/fontawesome-svg-core";
import {
  faAngleDoubleLeft,
  faAngleDoubleRight,
  faAngleLeft,
  faAngleRight,
  faFrown,
} from "@fortawesome/free-solid-svg-icons";
import {useStore} from "vuex";

library.add(faAngleRight, faAngleLeft, faFrown, faAngleDoubleRight, faAngleDoubleLeft);


export default {
  name: "AdventuresView",
  components: {
    FishingInstructorAdventureView,
    FontAwesomeIcon
  },
  data() {
    return {
      adventures: null,
      numberOfPages: null,
      currentPage: 0,
      pageSize: 30
    }
  },
  mounted() {
    const store = useStore();
    axios.get("/RentalObjects/getAdventuresInstructor", {headers: {
        Authorization: "Bearer " + store.state.access_token,
      },
      params: {
        page: this.currentPage,
        pageSize: this.pageSize,
        field: "name",
        email: store.state.email
      }},
    ).then(response => {
      this.adventures = response.data.content;
      this.numberOfPages = response.data.pages;
      console.log(this.adventures);
    });
  },
  methods: {
    firstPage() {
      this.currentPage = 0;
      this.callBackEnd();
    },
    nextPage() {
      this.currentPage += 1;
      this.callBackEnd();
    },
    prevPage() {
      this.currentPage -= 1;
      this.callBackEnd();
    },
    lastPage() {
      this.currentPage = this.numberOfPages - 1;
      this.callBackEnd();
    },
    callBackEnd() {
      axios.get("/RentalObjects/getAdventuresInstructor", {
            headers: {
              Authorization: "Bearer " + this.$store.state.access_token,
            },
            params: {
              page: this.currentPage,
              pageSize: this.pageSize,
              field: "name",
              email: this.$store.state.email
            }
          },
      ).then(response => {
        this.adventures = response.data.content;
        this.numberOfPages = response.data.pages;
      });
    }
  }
}
</script>

<style scoped>
  .container-fluid {
    padding-right:0;
    padding-left:0;
    margin-right:0;
    margin-left:0;
  }

  .header {
    background-color: rgb(68, 100, 173, 0.7);
    color: white;
  }

  h4.no-result {
    color: red;
    align-self: center;
  }

  h4 {
    vertical-align: center;
    margin-right: 3px;
    margin-left: 3px;
    color: #378220
  }

  .page-link {
    color: #378220;
  }
</style>