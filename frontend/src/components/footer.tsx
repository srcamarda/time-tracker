import { TbBrandGithubFilled } from "react-icons/tb";

export function Footer() {
  return (
    <footer className="absolute inset-x-0 bottom-0 bg-davys-gray text-white font-bold text-xs p-2">
      <a href="https://github.com/srcamarda/time-tracker" target="_blank" className='flex gap-2 justify-center items-center'>
        <TbBrandGithubFilled />
        
        Github
      </a>
    </footer>
  )
}