/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./htest/jvm/src/main/scala/server/*.scala"],
  theme: {
    extend: {},
  },
  plugins: [require("daisyui")],
}