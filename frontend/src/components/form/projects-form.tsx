import { Button } from '../ui/button'
import { DatePicker } from '../ui/date-picker'
import { DialogClose } from '../ui/dialog'
import { Input } from '../ui/input'
import { Label } from '../ui/label'
import { Textarea } from '../ui/textarea'

export function ProjectsForm() {
  return (
    <form className="mt-6 grid grid-cols-2 gap-x-8 gap-y-4">
      <div className="col-span-2 grid gap-2 text-left">
        <Label htmlFor="title">Título</Label>
        <Input id="title" placeholder="Digite o título" />
      </div>

      <div className="col-span-2 grid gap-2 text-left">
        <Label htmlFor="description">Descrição</Label>
        <Textarea id="description" placeholder="Descreva o projeto" />
      </div>

      <div className="grid gap-2 text-left max-xs:col-span-2">
        <Label htmlFor="startDate">Data de início</Label>
        <DatePicker />
      </div>

      <div className="grid gap-2 text-left max-xs:col-span-2">
        <Label htmlFor="endDate">Data de término</Label>
        <DatePicker />
      </div>

      <DialogClose asChild>
        <Button
          type="button"
          variant="outline"
          className="mt-6 border-peach font-bold text-peach duration-300 hover:bg-melon hover:text-white"
        >
          Cancelar
        </Button>
      </DialogClose>

      <Button
        type="submit"
        className="bg-peach font-bold text-white hover:bg-melon xs:mt-6"
      >
        Salvar
      </Button>
    </form>
  )
}
