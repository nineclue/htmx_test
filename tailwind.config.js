/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/main/scala/*.scala"],
  theme: {
    extend: {},
  },
  plugins: [require("daisyui")],
}