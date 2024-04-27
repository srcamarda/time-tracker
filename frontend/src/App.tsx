import { TbClock2 } from 'react-icons/tb'

import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"

import { Footer } from "./components/footer"

function App() {
  return (
    <>
      <div className="w-full h-screen flex items-center justify-center p-4">
        <div className="bg-white rounded-2xl px-12 py-16">
          <h1 className="font-bold text-3xl xs:text-4xl flex items-center justify-center gap-3 mb-4">
            <TbClock2 />
            Time Tracker
          </h1>
          
          <form className='text-dim-gray flex flex-col gap-3'>
            <div className='space-y-0.5'>
              <label htmlFor="username" className='text-sm'>Username</label>
              <Input id='username' placeholder='Digite o username' />
            </div>

            <div className='space-y-0.5'>
              <label htmlFor="password" className='text-sm'>Senha</label>
              <Input id='password' placeholder='Digite a senha' />
            </div>

            <Button type='submit' className='bg-peach text-white mt-4 font-bold hover:bg-melon'>Entrar</Button>
            <Button type='button' variant="outline" className='text-peach border-peach hover:bg-melon hover:text-white duration-300 font-bold'>Cadastrar</Button>

          </form>

        </div>

      </div>
      <Footer />
    </>
  )
}

export default App
