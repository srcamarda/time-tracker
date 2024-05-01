import { Button } from '../ui/button'
import { DatePicker } from '../ui/date-picker'
import { DialogClose } from '../ui/dialog'
import { Input } from '../ui/input'
import { Label } from '../ui/label'
import { Textarea } from '../ui/textarea'
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select'

export function TasksForm() {
  return (
    <form className="mt-6 grid grid-cols-6 gap-x-8 gap-y-4">
      <div className="col-span-4 grid gap-2 text-left">
        <Label htmlFor="title">Título</Label>
        <Input id="title" placeholder="Digite o título" />
      </div>

      <div className="col-span-2 grid gap-2 text-left">
        <Label htmlFor="tag">Tag</Label>
        <Select>
          <SelectTrigger className="w-full">
            <SelectValue placeholder="Selecione uma tag" />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="URGENT">URGENTE</SelectItem>
            <SelectItem value="IMPORTANT">IMPORTANTE</SelectItem>
            <SelectItem value="REVIEW">EM REVISÃO</SelectItem>
            <SelectItem value="IN_PROGRESS">EM PROGRESSO</SelectItem>
            <SelectItem value="COMPLETE">COMPLETO</SelectItem>
          </SelectContent>
        </Select>
      </div>

      <div className="col-span-6 grid gap-2 text-left">
        <Label htmlFor="member">Responsável</Label>
        <Select>
          <SelectTrigger className="w-full">
            <SelectValue id="member" placeholder="Selecione o responsável" />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="1">Paulo</SelectItem>
            <SelectItem value="2">Pedro</SelectItem>
            <SelectItem value="3">Davi</SelectItem>
            <SelectItem value="4">Edu</SelectItem>
            <SelectItem value="5">Thiago</SelectItem>
          </SelectContent>
        </Select>
      </div>

      <div className="col-span-6 grid gap-2 text-left">
        <Label htmlFor="description">Descrição</Label>
        <Textarea id="description" placeholder="Descreva o projeto" />
      </div>

      <div className="col-span-3 grid gap-3 text-left max-xs:col-span-6">
        <Label htmlFor="startDate">Data de início</Label>
        <DatePicker />
      </div>

      <div className="col-span-3 grid gap-3 text-left max-xs:col-span-6">
        <Label htmlFor="endDate">Data de término</Label>
        <DatePicker />
      </div>

      <DialogClose asChild>
        <Button
          type="button"
          variant="outline"
          className="col-span-3 mt-6 border-peach font-bold text-peach duration-300 hover:bg-melon hover:text-white max-xs:col-span-6"
        >
          Cancelar
        </Button>
      </DialogClose>

      <Button
        type="submit"
        className="col-span-3 bg-peach font-bold text-white hover:bg-melon max-xs:col-span-6 xs:mt-6"
      >
        Salvar
      </Button>
    </form>
  )
}
