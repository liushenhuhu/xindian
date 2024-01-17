<template>
  <div class="home">
    <div class="header">
      <img class="img1" :src="require('@/assets/images/logTitle.png')" />
      <img class="img2" :src="require('@/assets/images/homeIcon.png')" />
    </div>
    <div class="container">
      <img class="img3" v-show="!status" :src="require('@/assets/images/mp4Black.png')" @click="play"/>
      <video class="img3" v-show="status" ref="video" controls></video>
      <img class="img4" :src="require('@/assets/images/mainTitle.png')" />
    </div>
    <div>
      <img class="img5" :src="require('@/assets/images/ECGAI.png')" />
    </div>

  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.8.4",
      videoSrc:'https://ecg.mindyard.cn/data/mp4/0a76aee533301411abb8a7f58f3253e3.mp4',
      status:false,
    };
  },
  mounted() {
    this.$refs.video.addEventListener("play",()=>{
      this.status=true
    })
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
    play(){
      this.status=true
      this.$nextTick(()=>{
        this.$refs.video.src = this.videoSrc;
        this.$refs.video.play()
      })
    }
  },
};
</script>

<style scoped lang="scss">
.home {
  height: calc(100vh - 84px);
  padding: 45px 40px;
  background-image: url("~@/assets/images/homeBgImg.png");
  background-repeat: no-repeat;
  background-position: center center;
  background-attachment: scroll;
  background-size: cover;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  .header{
    display: flex;
    justify-content: space-between;
    .img1{
      width: 20%;
    }
    .img2{
      width: 3%;
      height: 50%;
    }
  }
  .container{
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 60%;
    .img3{
      width: 46%;
    }
    .img4{
      width: 51%;
      height: 70%;
    }
  }
  .img5{
    width: 100%;
  }
}
</style>
